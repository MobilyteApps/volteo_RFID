package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.TargetIdentifiers.IGs1TagEpcInformationProvider;

public abstract class AbstractGs1TagEpcRecogniser extends AbstractTagEpcRecogniser implements IGs1TagEpcInformationProvider {
    protected String mEpcFilterValue = "";

    public String filterValue() {
        return this.mEpcFilterValue;
    }
}
