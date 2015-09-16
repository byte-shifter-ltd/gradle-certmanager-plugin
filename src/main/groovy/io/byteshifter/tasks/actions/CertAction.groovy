package io.byteshifter.tasks.actions

import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

import javax.net.ssl.X509TrustManager
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

/**
 * CertAction
 * An abstract class which follows the Strategy design pattern for encapsulating the Gradle task action implementation.
 * This design is used to improve testability. We no longer need to directly instantiate a Gradle task to test,
 * which is disallowed by Gradle anyway.
 *
 * @author Sion Williams
 */
abstract class CertAction {

    private String host
    private int port
    private char[] passphrase
    Logger logger = Logging.getLogger(CertAction)


    CertAction(String host, int port, char[] passphrase) {
        assert host, "missing host parameter"
        assert port, "missing port parameter"
        assert passphrase, "missing passphrase parameter"

        this.host = host
        this.port = port
        this.passphrase = passphrase
    }

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

    abstract void runAction()
}
