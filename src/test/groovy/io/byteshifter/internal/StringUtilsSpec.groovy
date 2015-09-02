package io.byteshifter.internal

import spock.lang.Specification

/**
 * @author Sion Williams
 */
class StringUtilsSpec extends Specification {
    def "ToHexString returns correct value"() {
        given:
        byte[] byteArray = [255, 254, 253, 252, 251, 250] as byte[]

        expect:
        'ff fe fd fc fb fa ' == StringUtils.toHexString(byteArray)
    }
}
