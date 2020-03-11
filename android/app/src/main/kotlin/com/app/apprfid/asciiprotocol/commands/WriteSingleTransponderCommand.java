package com.app.apprfid.asciiprotocol.commands;

import com.app.apprfid.asciiprotocol.parameters.DatabankParameters;
import com.app.apprfid.asciiprotocol.responders.ITransponderReceivedDelegate;
import com.app.apprfid.asciiprotocol.responders.TransponderData;

public class WriteSingleTransponderCommand extends TransponderMemoryCommandBase {
    private ITransponderReceivedDelegate mTransponderReceivedDelegate = null;
    private boolean privateIsTransponderFound;
    private int privateWordsWritten;

    public WriteSingleTransponderCommand() {
        super(".ws");
    }

    private void setIsTransponderFound(boolean z) {
        this.privateIsTransponderFound = z;
    }

    private void setWordsWritten(int i) {
        this.privateWordsWritten = i;
    }

    public static WriteSingleTransponderCommand synchronousCommand() {
        WriteSingleTransponderCommand writeSingleTransponderCommand = new WriteSingleTransponderCommand();
        writeSingleTransponderCommand.setSynchronousCommandResponder(writeSingleTransponderCommand);
        return writeSingleTransponderCommand;
    }

    public void clearLastResponse() {
        super.clearLastResponse();
        setWordsWritten(0);
        setIsTransponderFound(false);
    }

    public final ITransponderReceivedDelegate getTransponderReceivedDelegate() {
        return this.mTransponderReceivedDelegate;
    }

    public final int getWordsWritten() {
        return this.privateWordsWritten;
    }

    public final boolean isTransponderFound() {
        return this.privateIsTransponderFound;
    }

    /* access modifiers changed from: protected */
    public boolean responseDidReceiveParameter(String str) {
        if (!DatabankParameters.parseParameterFor(this, str)) {
            return super.responseDidReceiveParameter(str);
        }
        return true;
    }

    public final void setTransponderReceivedDelegate(ITransponderReceivedDelegate iTransponderReceivedDelegate) {
        this.mTransponderReceivedDelegate = iTransponderReceivedDelegate;
    }

    public void transponderReceived(TransponderData transponderData, boolean z) {
        if (this.mTransponderReceivedDelegate != null) {
            this.mTransponderReceivedDelegate.transponderReceived(transponderData, z);
        }
    }
}
