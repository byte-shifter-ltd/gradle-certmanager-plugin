package io.byteshifter.tasks

import io.byteshifter.tasks.actions.PrintCertsAction

/**
 * @author Sion Williams
 */
public class PrintCertsTask extends CertTask {

    PrintCertsTask() {
        super("Print a list of available certificates available")
        certAction  = new PrintCertsAction(getHost(), getPort(), getPassphrase())
    }

    @Override
    void run() {
        certAction.runAction()
    }
}
