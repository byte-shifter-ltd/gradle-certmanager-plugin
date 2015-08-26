package io.byteshifter

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

import javax.net.ssl.*
import java.security.KeyStore
import java.security.MessageDigest
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

/**
 * https://code.google.com/p/misc-utils/wiki/JavaHttpsUrl#Adding_the_certificate_that_is_not_accepted
 * @author Sion Williams
 */
class InstallCertTask extends DefaultTask {

    @Input String host = 'opensso.in-silico.ch'
    @Input int port = 443
    @Input char[] passphrase = "changeit".toCharArray()

    @TaskAction
    void run() throws Exception {
        File jssecacerts = new File("jssecacerts")
        if (jssecacerts.isFile() == false) {
            char SEP = File.separatorChar
            File dir = new File(System.getProperty("java.home") + SEP + "lib" + SEP + "security")
            jssecacerts = new File(dir, "jssecacerts")
            if (jssecacerts.isFile() == false) {
                jssecacerts = new File(dir, "cacerts")
            }
        }

        logger.info "Loading KeyStore " + jssecacerts + "..."
        InputStream lIn = new FileInputStream(jssecacerts)
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType())
        ks.load(lIn, passphrase)
        lIn.close()

        SSLContext context = SSLContext.getInstance("TLS")
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(ks)
        X509TrustManager defaultTrustManager = (X509TrustManager)tmf.getTrustManagers()[0]
        SavingTrustManager tm = new SavingTrustManager(defaultTrustManager)
        context.init(null, [tm] as TrustManager[], null)
        SSLSocketFactory factory = context.getSocketFactory()

        logger.info "Opening connection to " + host + ":" + port + "..."
        SSLSocket socket = (SSLSocket)factory.createSocket(host, port)
        socket.setSoTimeout(10000)
        try {
            logger.info "Starting SSL handshake..."
            socket.startHandshake()
            socket.close()
            logger.info "No errors, certificate is already trusted"
        } catch (SSLException e) {
            e.printStackTrace(println())
        }

        X509Certificate[] chain = tm.chain
        if (chain == null) {
            logger.info "Could not obtain server certificate chain"
            return
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))

        logger.info "Server sent " + chain.length + " certificate(s):"
        MessageDigest sha1 = MessageDigest.getInstance("SHA1")
        MessageDigest md5 = MessageDigest.getInstance("MD5")
        for (int i = 0; i < chain.length; i++) {
            X509Certificate cert = chain[i]
            logger.info " " + (i + 1) + " Subject " + cert.getSubjectDN()
            logger.info "   Issuer  " + cert.getIssuerDN()
            sha1.update(cert.getEncoded())
            logger.info "   sha1    " + toHexString(sha1.digest())
            md5.update(cert.getEncoded())
            logger.info "   md5     " + toHexString(md5.digest())
        }

        logger.info "Enter certificate to add to trusted keystore or 'q' to quit: [1]"
        String line = reader.readLine().trim()
        int k
        try {
            k = (line.length() == 0) ? 0 : Integer.parseInt(line) - 1
        } catch (NumberFormatException e) {
            logger.info "KeyStore not changed"
        }

        X509Certificate cert = chain[k]
        String alias = host + "-" + (k + 1)
        ks.setCertificateEntry(alias, cert)

        OutputStream out = new FileOutputStream("jssecacerts")
        ks.store(out, passphrase)
        out.close()

        logger.info cert
        logger.info "Added certificate to keystore 'jssecacerts' using alias '" + alias + "'"
    }

    private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray()

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 3)
        for (int b : bytes) {
            b &= 0xff;
            sb.append(HEXDIGITS[b >> 4])
            sb.append(HEXDIGITS[b & 15])
            sb.append(' ')
        }
        sb.toString()
    }

    private static class SavingTrustManager implements X509TrustManager {

        private final X509TrustManager tm
        private X509Certificate[] chain

        SavingTrustManager(X509TrustManager tm) {
            this.tm = tm
        }

        public X509Certificate[] getAcceptedIssuers() {
            throw new UnsupportedOperationException()
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            throw new UnsupportedOperationException()
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            this.chain = chain
            tm.checkServerTrusted(chain, authType)
        }
    }

}
