package com.app.apprfid.asciiprotocol.responders;

public interface IAsciiCommandResponder {
    void clearLastResponse();

    boolean isResponseFinished();

    boolean processReceivedLine(String str, boolean z);
}
