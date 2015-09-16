package io.byteshifter.tasks

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Specification for the printCerts task
 *
 * @author Sion Williams
 */
class PrintCertsTaskSpec extends Specification {
    Project project
    static final TASK_NAME = 'printCerts'

    void setup() {
        project = ProjectBuilder.builder().build()
    }

    def "PrintCertsTask can be added to project"() {
        expect: "no task added by default"
        project.tasks.findByName(TASK_NAME) == null

        when:
        project.task(TASK_NAME, type: PrintCertsTask)

        then:
        Task task = project.tasks.findByName(TASK_NAME)
        task != null
    }

    def "PrintCertsTask has correct defaults"() {
        given:
        project.task(TASK_NAME, type: PrintCertsTask)

        expect:
        Task task = project.tasks.findByName(TASK_NAME)
        task != null
        task.host == 'opensso.in-silico.ch'
        task.port == 443
        task.passphrase == "changeit".toCharArray()
    }

    def "PrintCertsTask defaults can be overridden"() {
        given:
        project.task(TASK_NAME, type: PrintCertsTask) {
            host = 'localhost'
            port = 9000
            passphrase = "welcome1".toCharArray()
        }

        expect:
        Task task = project.tasks.findByName(TASK_NAME)
        task != null
        task.host == 'localhost'
        task.port == 9000
        task.passphrase == "welcome1".toCharArray()
    }
}
