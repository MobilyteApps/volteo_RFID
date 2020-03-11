package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.enumerations.DeleteConfirmation;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;
import com.app.apprfid.asciiprotocol.responders.IFileLineReceivedDelegate;

public class ReadAutorunFileCommand extends AsciiSelfResponderCommandBase {
    private boolean mFileLinesHaveStarted = false;
    private DeleteConfirmation privateDeleteFile = DeleteConfirmation.NOT_SPECIFIED;
    private IFileLineReceivedDelegate privateFileLineReceivedDelegate = null;

    public ReadAutorunFileCommand() {
        super(".ra");
    }

    public static ReadAutorunFileCommand synchronousCommand() {
        ReadAutorunFileCommand readAutorunFileCommand = new ReadAutorunFileCommand();
        readAutorunFileCommand.setSynchronousCommandResponder(readAutorunFileCommand);
        return readAutorunFileCommand;
    }

    public void clearLastResponse() {
        super.clearLastResponse();
        this.mFileLinesHaveStarted = false;
    }

    public String getCommandLine() {
        String commandLine = super.getCommandLine();
        if (getDeleteFile() != DeleteConfirmation.YES) {
            return commandLine;
        }
        return commandLine + String.format("-d%s", new Object[]{getDeleteFile().getArgument()});
    }

    public DeleteConfirmation getDeleteFile() {
        return this.privateDeleteFile;
    }

    public IFileLineReceivedDelegate getFileLineReceivedDelegate() {
        return this.privateFileLineReceivedDelegate;
    }

    /* access modifiers changed from: protected */
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (!getResponseStarted() || !this.mFileLinesHaveStarted) {
            if (super.processReceivedLine(str, str2, str3, z)) {
                return true;
            }
            if (!getResponseStarted()) {
                return false;
            }
            if (!"AB".equals(str2)) {
                return false;
            }
            this.mFileLinesHaveStarted = true;
            return true;
        } else if ("AE".equals(str2)) {
            this.mFileLinesHaveStarted = false;
            return true;
        } else if (getFileLineReceivedDelegate() == null) {
            return true;
        } else {
            getFileLineReceivedDelegate().fileLineReceived(str, z);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void responseDidFinish(boolean z) {
        if (getFileLineReceivedDelegate() != null) {
            getFileLineReceivedDelegate().fileLineReceived(null, false);
        }
        super.responseDidFinish(z);
    }

    public void setDeleteFile(DeleteConfirmation deleteConfirmation) {
        this.privateDeleteFile = deleteConfirmation;
    }

    public void setFileLineReceivedDelegate(IFileLineReceivedDelegate iFileLineReceivedDelegate) {
        this.privateFileLineReceivedDelegate = iFileLineReceivedDelegate;
    }
}
