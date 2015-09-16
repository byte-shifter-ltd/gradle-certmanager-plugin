package io.byteshifter.tasks

import io.byteshifter.tasks.actions.CertAction
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * @author Sion Williams
 */
abstract class CertTask extends DefaultTask {
    @Input
    String host = 'opensso.in-silico.ch'

    @Input
    int port = 443

    @Input
    char[] passphrase = "changeit".toCharArray()

    protected CertAction certAction

    CertTask(String description) {
        this.description = description
        group = 'Certificate Manager'
    }

    @TaskAction
    void exectuteTask() {
        run()
    }

    abstract void run()
}
