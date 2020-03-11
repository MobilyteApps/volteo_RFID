package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.enumerations.DeleteConfirmation;
import com.app.apprfid.asciiprotocol.enumerations.TriState;
import com.app.apprfid.asciiprotocol.parameters.CommandParameters;
import com.app.apprfid.asciiprotocol.parameters.ICommandParameters;
import com.app.apprfid.asciiprotocol.responders.AsciiSelfResponderCommandBase;
import com.app.apprfid.asciiprotocol.responders.IFileLineReceivedDelegate;

public class ReadLogFileCommand extends AsciiSelfResponderCommandBase implements ICommandParameters {
    private boolean mFileLinesHaveStarted = false;
    private TriState privateCommandLoggingEnabled = TriState.NOT_SPECIFIED;
    private DeleteConfirmation privateDeleteFile = DeleteConfirmation.NOT_SPECIFIED;
    private IFileLineReceivedDelegate privateFileLineReceivedDelegate = null;
    private TriState privateReadParameters;
    private TriState privateResetParameters;
    private TriState privateTakeNoAction;

    public ReadLogFileCommand() {
        super(".rl");
    }

    public static ReadLogFileCommand synchronousCommand() {
        ReadLogFileCommand readLogFileCommand = new ReadLogFileCommand();
        readLogFileCommand.setSynchronousCommandResponder(readLogFileCommand);
        return readLogFileCommand;
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        CommandParameters.appendToCommandLine(this, sb);
        if (getCommandLoggingEnabled() != TriState.NOT_SPECIFIED) {
            sb.append(String.format("-c%s", new Object[]{getCommandLoggingEnabled().getArgument()}));
        }
        if (getDeleteFile() == DeleteConfirmation.YES) {
            sb.append(String.format("-d%s", new Object[]{getDeleteFile().getArgument()}));
        }
    }

    public void clearLastResponse() {
        super.clearLastResponse();
        this.mFileLinesHaveStarted = false;
    }

    public TriState getCommandLoggingEnabled() {
        return this.privateCommandLoggingEnabled;
    }

    public DeleteConfirmation getDeleteFile() {
        return this.privateDeleteFile;
    }

    public IFileLineReceivedDelegate getFileLineReceivedDelegate() {
        return this.privateFileLineReceivedDelegate;
    }

    public final TriState getReadParameters() {
        return this.privateReadParameters;
    }

    public final TriState getResetParameters() {
        return this.privateResetParameters;
    }

    public final TriState getTakeNoAction() {
        return this.privateTakeNoAction;
    }

    public final boolean implementsReadParameters() {
        return true;
    }

    public final boolean implementsResetParameters() {
        return true;
    }

    public final boolean implementsTakeNoAction() {
        return true;
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
            if (!"LB".equals(str2)) {
                return false;
            }
            this.mFileLinesHaveStarted = true;
            return true;
        } else if ("LE".equals(str2)) {
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

    /* access modifiers changed from: protected */
    public boolean responseDidReceiveParameter(String str) {
        if (CommandParameters.parseParameterFor(this, str)) {
            return true;
        }
        if (str.length() >= 1 && str.startsWith("c")) {
            setCommandLoggingEnabled(TriState.Parse(str.substring(1)));
            return true;
        } else if (str.length() < 1 || !str.startsWith("d")) {
            return super.responseDidReceiveParameter(str);
        } else {
            if (str.length() <= 1) {
                return true;
            }
            setDeleteFile(DeleteConfirmation.Parse(str.substring(1)));
            return true;
        }
    }

    public void setCommandLoggingEnabled(TriState triState) {
        this.privateCommandLoggingEnabled = triState;
    }

    public void setDeleteFile(DeleteConfirmation deleteConfirmation) {
        this.privateDeleteFile = deleteConfirmation;
    }

    public void setFileLineReceivedDelegate(IFileLineReceivedDelegate iFileLineReceivedDelegate) {
        this.privateFileLineReceivedDelegate = iFileLineReceivedDelegate;
    }

    public final void setReadParameters(TriState triState) {
        this.privateReadParameters = triState;
    }

    public final void setResetParameters(TriState triState) {
        this.privateResetParameters = triState;
    }

    public final void setTakeNoAction(TriState triState) {
        this.privateTakeNoAction = triState;
    }
}
