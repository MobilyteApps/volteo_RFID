package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.TargetIdentifiers.IGs1CompanyPrefixProvider;

public abstract class AbstractGs1InputRecogniser implements IInputRecogniser {
    protected IGs1CompanyPrefixProvider mGs1CompanyPrefixProvider;

    public AbstractGs1InputRecogniser(IGs1CompanyPrefixProvider iGs1CompanyPrefixProvider) {
        this.mGs1CompanyPrefixProvider = iGs1CompanyPrefixProvider;
    }
}
