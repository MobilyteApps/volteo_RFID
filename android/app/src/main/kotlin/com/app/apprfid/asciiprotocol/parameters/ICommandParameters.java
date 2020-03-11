package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.enumerations.TriState;

public interface ICommandParameters {
    TriState getReadParameters();

    TriState getResetParameters();

    TriState getTakeNoAction();

    boolean implementsReadParameters();

    boolean implementsResetParameters();

    boolean implementsTakeNoAction();

    void setReadParameters(TriState triState);

    void setResetParameters(TriState triState);

    void setTakeNoAction(TriState triState);
}
