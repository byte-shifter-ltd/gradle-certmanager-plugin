package io.byteshifter.tasks

import io.byteshifter.tasks.actions.PrintCertsAction

/**
 * @author Sion Williams
 */
public class InstallCertTask extends CertTask{

    InstallCertTask() {
        super("Installs a requested Certificate into your store")
        this.certAction = new PrintCertsAction(getHost(), getPort(), getPassphrase())
    }

    @Override
    void run() {
        certAction.runAction()
    }
}
