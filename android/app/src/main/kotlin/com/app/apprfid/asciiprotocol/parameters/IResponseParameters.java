package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.enumerations.TriState;

public interface IResponseParameters {
    TriState getIncludeDateTime();

    TriState getUseAlert();

    void setIncludeDateTime(TriState triState);

    void setUseAlert(TriState triState);
}
