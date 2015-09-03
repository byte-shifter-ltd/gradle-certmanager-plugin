package io.byteshifter.internal

import spock.lang.Specification

/**
 * @author Sion Williams
 */
class StringUtilsSpec extends Specification {
    def "formattedToHexString returns correct value"() {
        given:
        byte[] byteArray = [255, 254, 253, 252, 251, 250] as byte[]

        expect:
        'ff fe fd fc fb fa ' == StringUtils.formattedToHexString(byteArray)
    }

    def "formattedToHexString returns null if input is null"() {
        expect:
        null == StringUtils.formattedToHexString(null)
    }

    def "toHexString returns correct value"() {
        given:
        byte[] byteArray = [255, 254, 253, 252, 251, 250] as byte[]

        expect:
        'FFFEFDFCFBFA' == StringUtils.toHexString(byteArray)
    }

    def "toHexString returns null if input is null"() {
        expect:
        null == StringUtils.toHexString(null)
    }
}
