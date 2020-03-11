package com.app.apprfid.asciiprotocol.responders;

import java.util.List;

public interface IAsciiResponse {
    String getErrorCode();

    List getMessages();

    List getParameters();

    List getResponse();

    boolean isSuccessful();
}
