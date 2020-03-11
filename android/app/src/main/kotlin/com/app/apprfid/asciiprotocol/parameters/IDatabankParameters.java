package com.app.apprfid.asciiprotocol.parameters;

import com.app.apprfid.asciiprotocol.enumerations.Databank;

public interface IDatabankParameters {
    Databank getBank();

    byte[] getData();

    int getLength();

    int getOffset();

    void setBank(Databank databank);

    void setData(byte[] bArr);

    void setLength(int i);

    void setOffset(int i);
}
