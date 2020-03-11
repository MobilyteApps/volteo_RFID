package com.app.apprfid.findmodel.TargetIdentifiers;


import com.app.apprfid.findmodel.EncodingType;
import com.app.apprfid.findmodel.InputRecognisers.AbstractGs1GraiPaddedPartitionInputRecogniser;
import com.app.apprfid.findmodel.InputRecognisers.AbstractGs1InputRecogniser;
import com.app.apprfid.findmodel.InputRecognisers.Gs1GraiInputRecogniser;
import com.app.apprfid.findmodel.InputRecognisers.Gs1GraiRcnInputRecogniser;
import com.app.apprfid.findmodel.InputRecognisers.Gs1GraiShortCodeInputRecogniser;
import com.app.apprfid.util.b;


public class Grai96TargetIdentifier extends AbstractGs1TargetIdentifier {
    protected static String sEpcHeader = "00110011";
    protected static String sZeroSerialNumber = "00000000000000000000000000000000000000";

    public Grai96TargetIdentifier() {
        this.mInputRecognisers = new AbstractGs1InputRecogniser[]{new Gs1GraiInputRecogniser(this), new Gs1GraiRcnInputRecogniser(this), new Gs1GraiShortCodeInputRecogniser(this)};
    }

    public String description() {
        return "GRAI-96 Identifier";
    }

    public EncodingType encodingType() {
        return EncodingType.GRAI_96;
    }

    public String epcEncoding() {
        return this.mEpcEncoding;
    }

    public int epcMatchingBitLength() {
        return 58;
    }

    /* access modifiers changed from: protected */
    public void validateValue() {
        super.validateValue();
        if (isValueValid()) {
            String str = ((sEpcHeader + b.b(3, this.mEpcFilter)) + ((AbstractGs1GraiPaddedPartitionInputRecogniser) inputRecogniser()).partitionAsBinary()) + sZeroSerialNumber;
            this.mBinaryEpcEncoding = str;
            this.mEpcEncoding = b.c(24, str);
        }
    }
}
