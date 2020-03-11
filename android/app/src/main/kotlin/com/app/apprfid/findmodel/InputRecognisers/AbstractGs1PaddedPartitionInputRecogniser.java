package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.TargetIdentifiers.IGs1CompanyPrefixProvider;

public abstract class AbstractGs1PaddedPartitionInputRecogniser extends AbstractGs1InputRecogniser implements IGs1PaddedPartitionProvider {
    protected String mPaddedPartition = "";

    public AbstractGs1PaddedPartitionInputRecogniser(IGs1CompanyPrefixProvider iGs1CompanyPrefixProvider) {
        super(iGs1CompanyPrefixProvider);
    }

    public String partitionAsBinary() {
        return this.mPaddedPartition;
    }
}
