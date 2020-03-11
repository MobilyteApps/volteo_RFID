package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.enumerations.TriState;

public interface ITransponderParameters {
    String getAccessPassword();

    TriState getIncludeChecksum();

    TriState getIncludeIndex();

    TriState getIncludePC();

    TriState getIncludeTransponderRssi();

    void setAccessPassword(String str);

    void setIncludeChecksum(TriState triState);

    void setIncludeIndex(TriState triState);

    void setIncludePC(TriState triState);

    void setIncludeTransponderRssi(TriState triState);
}
