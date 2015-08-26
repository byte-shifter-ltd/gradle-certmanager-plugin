package io.byteshifter

import nebula.test.PluginProjectSpec

/**
 * Specification for the plugin implementation
 *
 * @author Sion Williams
 */
class CertManagerPluginSpec extends PluginProjectSpec {
    static final String PLUGIN_ID = 'io.byteshifter.certman'

    @Override
    String getPluginName() {
        return PLUGIN_ID
    }

    def setup() {
        project.apply plugin: pluginName
    }

    def "apply creates installCert task"() {
        expect: project.tasks.findByName( 'installCert' )
    }
}
