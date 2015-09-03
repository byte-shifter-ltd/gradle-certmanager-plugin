package io.byteshifter.tasks

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Specification for the installCerts task
 *
 * @author Sion Williams
 */
class InstallCertTaskSpec extends Specification {
    Project project
    static final TASK_NAME = 'installCerts'

    void setup() {
        project = ProjectBuilder.builder().build()
    }

    def "InstallCertTask can be added to project"() {
        expect: "no task added by default"
        project.tasks.findByName(TASK_NAME) == null

        when:
        project.task(TASK_NAME, type: InstallCertTask)

        then:
        Task task = project.tasks.findByName(TASK_NAME)
        task != null
    }

    def "InstallCertTask has correct defaults"() {
        given:
        project.task(TASK_NAME, type: InstallCertTask)

        expect:
        Task task = project.tasks.findByName(TASK_NAME)
        task.host == 'opensso.in-silico.ch'
        task.port == 443
        task.passphrase == "changeit".toCharArray()
    }

    @Ignore("TODO")
    def "InstallCertTask defaults can be overridden"() {
        given:
        project.task(TASK_NAME, type: InstallCertTask){
            host = 'localhost'
            port = 9000
            passphrase = "welcome1".toCharArray()
        }

        expect:
        Task task = project.tasks.findByName(TASK_NAME)
        task != null
        task.host == 'opensso.in-silico.ch'
        task.port == 443
        task.passphrase == "welcome1".toCharArray()
    }
}
