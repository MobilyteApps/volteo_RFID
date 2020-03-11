package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.enumerations.SelectAction;
import com.app.apprfid.asciiprotocol.enumerations.SelectTarget;

public interface ISelectControlParameters {
    SelectAction getSelectAction();

    SelectTarget getSelectTarget();

    void setSelectAction(SelectAction selectAction);

    void setSelectTarget(SelectTarget selectTarget);
}
