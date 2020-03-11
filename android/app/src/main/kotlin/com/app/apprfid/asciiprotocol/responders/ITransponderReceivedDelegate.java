package com.app.apprfid.asciiprotocol.responders;

public interface ITransponderReceivedDelegate {
    void transponderReceived(TransponderData transponderData, boolean z);
}
