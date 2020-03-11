package com.app.apprfid.asciiprotocol.responders;

public class SynchronousDispatchResponder implements IAsciiCommandResponder {
    private IAsciiCommandResponder privateSynchronousCommandResponder;

    public final void clearLastResponse() {
        IAsciiCommandResponder synchronousCommandResponder = getSynchronousCommandResponder();
        if (synchronousCommandResponder != null) {
            synchronousCommandResponder.clearLastResponse();
        }
    }

    public final IAsciiCommandResponder getSynchronousCommandResponder() {
        return this.privateSynchronousCommandResponder;
    }

    public final boolean isResponseFinished() {
        IAsciiCommandResponder synchronousCommandResponder = getSynchronousCommandResponder();
        if (synchronousCommandResponder != null) {
            return synchronousCommandResponder.isResponseFinished();
        }
        return false;
    }

    public final boolean processReceivedLine(String str, boolean z) {
        IAsciiCommandResponder synchronousCommandResponder = getSynchronousCommandResponder();
        if (synchronousCommandResponder != null) {
            return synchronousCommandResponder.processReceivedLine(str, z);
        }
        return false;
    }

    public final void setSynchronousCommandResponder(IAsciiCommandResponder iAsciiCommandResponder) {
        this.privateSynchronousCommandResponder = iAsciiCommandResponder;
    }
}
