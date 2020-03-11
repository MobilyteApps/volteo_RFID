package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.enumerations.Databank;

public interface ISelectMaskParameters {
    Databank getSelectBank();

    String getSelectData();

    int getSelectLength();

    int getSelectOffset();

    void setSelectBank(Databank databank);

    void setSelectData(String str);

    void setSelectLength(int i);

    void setSelectOffset(int i);
}
