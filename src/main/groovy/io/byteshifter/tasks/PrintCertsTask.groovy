package io.byteshifter.tasks

import io.byteshifter.internal.StringUtils

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLException
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import java.security.KeyStore
import java.security.MessageDigest
import java.security.cert.X509Certificate

/**
 * This task will retrieve a list of available certs and print them to the user
 *
 * @author Sion Williams
 */
class PrintCertsTask extends AbstractCertTask {

    @Override
    void run() {
        File file = createCert()
        logger.info("Loading KeyStore " + file + "...");
        InputStream inputStream = new FileInputStream(file);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(inputStream, passphrase);
        inputStream.close();

        SSLContext context = SSLContext.getInstance("TLS");
        TrustManagerFactory tmf =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        X509TrustManager defaultTrustManager = (X509TrustManager)tmf.getTrustManagers()[0];
        SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
        context.init(null, [tm] as TrustManager[], null);
        SSLSocketFactory factory = context.getSocketFactory();

        logger.info("Opening connection to " + host + ":" + port + "...");
        SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
        socket.setSoTimeout(10000);
        try {
            logger.info("Starting SSL handshake...");
            socket.startHandshake();
            socket.close();
            logger.info(" ");
            logger.info("No errors, certificate is already trusted");
        } catch (SSLException e) {
            logger.info(" ");
            e.printStackTrace(System.out);
        }

        // This is the list of certificates returned by the server
        X509Certificate[] chain = tm.chain;
        if (chain == null) {
            logger.info("Could not obtain server certificate chain");
            return;
        }

        prettyPrintCerts(chain)
    }

    /**
     * Given a chain of X509 Certificates iterate through pretty printing each one
     * @param chain X509Certificate
     */
    private void prettyPrintCerts(X509Certificate[] chain) {
        logger.info "************************************************"
        logger.info "Server sent " + chain.length + " certificate(s):"
        logger.info "************************************************"
        MessageDigest sha1 = MessageDigest.getInstance("SHA1")
        MessageDigest md5 = MessageDigest.getInstance("MD5")
        for (int i = 0; i < chain.length; i++) {
            X509Certificate cert = chain[i]
            logger.info " " + (i + 1) + " Subject " + cert.getSubjectDN()
            logger.info "   Issuer  " + cert.getIssuerDN()
            sha1.update(cert.getEncoded())
            logger.info "   sha1    " + StringUtils.formattedToHexString(sha1.digest())
            md5.update(cert.getEncoded())
            logger.info "   md5     " + StringUtils.formattedToHexString(md5.digest())
            logger.info " "
        }
    }
}