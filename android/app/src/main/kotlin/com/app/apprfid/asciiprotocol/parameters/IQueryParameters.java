package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.enumerations.QuerySelect;
import com.app.apprfid.asciiprotocol.enumerations.QuerySession;
import com.app.apprfid.asciiprotocol.enumerations.QueryTarget;

public interface IQueryParameters {
    QuerySelect getQuerySelect();

    QuerySession getQuerySession();

    QueryTarget getQueryTarget();

    void setQuerySelect(QuerySelect querySelect);

    void setQuerySession(QuerySession querySession);

    void setQueryTarget(QueryTarget queryTarget);
}
