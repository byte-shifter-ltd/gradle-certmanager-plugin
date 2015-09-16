package io.byteshifter.internal

import io.byteshifter.tasks.actions.CertAction
import spock.lang.Specification

/**
 * @author Sion Williams
 */
class SavingTrustManagerSpec extends Specification {
    def "GetAcceptedIssuers throws exception"() {
        given:
        CertAction.SavingTrustManager trustManager = new CertAction.SavingTrustManager()

        when:
        trustManager.getAcceptedIssuers()

        then:
        thrown(UnsupportedOperationException)
    }
}
