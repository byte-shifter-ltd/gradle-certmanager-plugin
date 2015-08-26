package io.byteshifter

import nebula.test.IntegrationSpec
import nebula.test.functional.ExecutionResult

/**
 * @author Sion Williams
 */
class CertManagerPluginIntegTest extends IntegrationSpec {

    def 'apply plugin, setup and run installCert which passes'() {
        setup:
        buildFile << '''
            apply plugin: 'io.byteshifter.certman'
        '''.stripIndent()

        when:
        ExecutionResult result = runTasksSuccessfully('installCert')

        then:
        result.standardOutput.contains('Added certificate to keystore \'jssecacerts\' using alias')
    }
}
