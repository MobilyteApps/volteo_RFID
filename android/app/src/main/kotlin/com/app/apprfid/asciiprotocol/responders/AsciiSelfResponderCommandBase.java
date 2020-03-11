package com.app.apprfid.asciiprotocol.responders;

import com.app.apprfid.asciiprotocol.commands.IAsciiCommand;

public abstract class AsciiSelfResponderCommandBase extends AsciiCommandLibraryResponderBase implements IAsciiCommand {
    private static int commandId = 0;
    private double privateMaxSynchronousWaitTime;
    private IAsciiCommandResponder privateSynchronousCommandResponder;

    protected AsciiSelfResponderCommandBase(String str) {
        super(str);
        setMaxSynchronousWaitTime(3.0d);
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        int i = commandId;
        commandId = i + 1;
        sb.append(String.format("%06d", new Object[]{Integer.valueOf(i)}));
    }

    public final double getMaxSynchronousWaitTime() {
        return this.privateMaxSynchronousWaitTime;
    }

    public final IAsciiCommandResponder getSynchronousCommandResponder() {
        return this.privateSynchronousCommandResponder;
    }

    public final void setMaxSynchronousWaitTime(double d) {
        this.privateMaxSynchronousWaitTime = d;
    }

    public final void setSynchronousCommandResponder(IAsciiCommandResponder iAsciiCommandResponder) {
        this.privateSynchronousCommandResponder = iAsciiCommandResponder;
    }
}
