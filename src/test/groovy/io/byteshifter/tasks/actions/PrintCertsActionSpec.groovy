package io.byteshifter.tasks.actions

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Sion Williams
 */
class PrintCertsActionSpec extends Specification {
    @Unroll
    def "Fails if constructor parameter #nullParamName is null"() {
        when:
        new PrintCertsAction(host, port, passphrase)

        then:
        Throwable t = thrown(AssertionError)
        t.message.startsWith("missing $nullParamName parameter")

        where:
        host     | port | passphrase                   | nullParamName
        null     | 80   | 'myPassphrase'.toCharArray() | 'host'
        'myHost' | 80   | null                         | 'passphrase'
    }

    def "Can create instance if all constructor parameter are provided"() {
        when:
        new PrintCertsAction('myHost', 80, 'myPassword'.toCharArray())

        then:
        noExceptionThrown()
    }

    def "Cant create instance if null parameter provided for port"() {
        when:
        new PrintCertsAction('myHost', null, 'myPassword'.toCharArray())

        then:
        thrown(GroovyRuntimeException)
    }
}
