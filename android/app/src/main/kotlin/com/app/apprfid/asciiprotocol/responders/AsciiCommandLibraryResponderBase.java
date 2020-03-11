package com.app.apprfid.asciiprotocol.responders;


import com.app.apprfid.b.b;

public abstract class AsciiCommandLibraryResponderBase extends AsciiCommandResponderBase {
    public static final String LibraryCommandId = "LCMD";
    private boolean privateCaptureNonLibraryResponses;

    protected AsciiCommandLibraryResponderBase(String str) {
        super(str);
        setCaptureNonLibraryResponses(false);
    }

    /* access modifiers changed from: protected */
    public void buildCommandLine(StringBuilder sb) {
        super.buildCommandLine(sb);
        sb.append(LibraryCommandId);
        sb.append(" ");
    }

    public final boolean captureNonLibraryResponses() {
        return this.privateCaptureNonLibraryResponses;
    }

    /* access modifiers changed from: protected */
    public boolean processReceivedLine(String str, String str2, String str3, boolean z) {
        if (getResponseStarted() || !"CS".equals(str2) || ((!str3.startsWith(getCommandName()) && !b.a(getCommandName())) || captureNonLibraryResponses() || str3.substring(3).indexOf(LibraryCommandId) >= 0)) {
            return super.processReceivedLine(str, str2, str3, z);
        }
        return false;
    }

    public final void setCaptureNonLibraryResponses(boolean z) {
        this.privateCaptureNonLibraryResponses = z;
    }
}
