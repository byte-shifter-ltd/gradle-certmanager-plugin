package io.byteshifter

import nebula.test.IntegrationSpec
import nebula.test.functional.ExecutionResult
import spock.lang.Ignore

/**
 * @author Sion Williams
 */
class CertManagerPluginIntegTest extends IntegrationSpec {

    @Ignore
    def 'apply plugin, setup and run installCert which passes'() {
        setup:
        createBuild()

        when:
        ExecutionResult result = runTasksSuccessfully('installCert')

        then:
        result.standardOutput.contains('Added certificate to keystore \'jssecacerts\' using alias')
    }

    def 'apply plugin, setup and run printCert which passes'() {
        setup:
        createBuild()

        when:
        ExecutionResult result = runTasksSuccessfully('printCerts')

        then:
        result.standardOutput.contains('Server sent 3 certificate(s):')
    }

    private File createBuild() {
        buildFile << '''
            apply plugin: 'io.byteshifter.certman'
        '''.stripIndent()
    }
}
