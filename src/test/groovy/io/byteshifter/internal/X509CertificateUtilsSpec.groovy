package io.byteshifter.internal

import spock.lang.Specification

import java.security.Principal
import java.security.cert.X509Certificate

/**
 * @author Sion Williams
 */
class X509CertificateUtilsSpec extends Specification {
    X509Certificate mockCert

    def setup() {
        mockCert = Mock()
    }

    def "pretty print calls subjectDN"() {
        when:
        X509CertificateUtils.prettyPrint(mockCert)

        then:
        1 * mockCert.getSubjectDN()
        0 * _   // don't allow any other interaction
    }

    def "pretty print returns correct string"() {
        setup:
        X509Certificate stubCert = Stub()
        Principal princ = Stub()
        stubCert.getSubjectDN() >> princ
        princ.toString() >> "Test"

        when:
        def output = X509CertificateUtils.prettyPrint(stubCert)

        then:
        output == "Subject: Test"
    }
}
