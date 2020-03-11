package com.app.apprfid.asciiprotocol.responders;

import android.util.Log;

public class LoggerResponder implements IAsciiCommandResponder {
    public final void clearLastResponse() {
    }

    public final boolean isResponseFinished() {
        return false;
    }

    public final boolean processReceivedLine(String str, boolean z) {
        Log.i("LoggerResponder", ">" + str);
        return false;
    }
}
