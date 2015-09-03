package io.byteshifter.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

import javax.net.ssl.X509TrustManager
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

/**
 * @author Sion Williams
 */
abstract class AbstractCertTask extends DefaultTask {
    @Input String host = 'opensso.in-silico.ch'
    @Input int port = 443
    @Input char[] passphrase = "changeit".toCharArray()

    @TaskAction
    void exectuteTask() {
        run()
    }

    abstract void run()

    protected File createCert() {
        File file = new File("jssecacerts");
        if (file.isFile() == false) {
            char separatorChar = File.separatorChar;
            File dir = new File(System.getProperty("java.home") + separatorChar
                    + "lib" + separatorChar + "security");
            file = new File(dir, "jssecacerts");
            if (file.isFile() == false) {
                file = new File(dir, "cacerts");
            }
        }
        file
    }

    protected static class SavingTrustManager implements X509TrustManager {

        private final X509TrustManager tm;
        protected X509Certificate[] chain;

        SavingTrustManager(X509TrustManager tm) {
            this.tm = tm;
        }

        public X509Certificate[] getAcceptedIssuers() {
            throw new UnsupportedOperationException();
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            throw new UnsupportedOperationException();
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            this.chain = chain;
            tm.checkServerTrusted(chain, authType);
        }
    }
}
