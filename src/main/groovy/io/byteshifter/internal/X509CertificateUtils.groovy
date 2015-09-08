package io.byteshifter.internal

import java.security.cert.X509Certificate

/**
 * @author Sion Williams
 */
class X509CertificateUtils {

    static def prettyPrint(X509Certificate x509Certificate) {
        return "Subject: ${x509Certificate.getSubjectDN()}"
    }
}
