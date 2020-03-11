package com.app.apprfid.findmodel.TargetIdentifiers;


import com.app.apprfid.findmodel.EncodingType;
import com.app.apprfid.findmodel.InputRecognisers.AbstractGs1GtinPaddedPartitionInputRecogniser;
import com.app.apprfid.findmodel.InputRecognisers.AbstractGs1InputRecogniser;
import com.app.apprfid.findmodel.InputRecognisers.Gs1GtinInputRecogniser;
import com.app.apprfid.findmodel.InputRecognisers.Gs1GtinRcnInputRecogniser;
import com.app.apprfid.findmodel.InputRecognisers.Gs1GtinShortCodeInputRecogniser;
import com.app.apprfid.util.b;


public class Sgtin96TargetIdentifier extends AbstractGs1TargetIdentifier {
    protected static String sEpcHeader = "00110000";
    protected static String sZeroSerialNumber = "00000000000000000000000000000000000000";

    public Sgtin96TargetIdentifier() {
        this.mInputRecognisers = new AbstractGs1InputRecogniser[]{new Gs1GtinInputRecogniser(this), new Gs1GtinRcnInputRecogniser(this), new Gs1GtinShortCodeInputRecogniser(this)};
    }

    public String description() {
        return "SGTIN-96 Identifier";
    }

    public EncodingType encodingType() {
        return EncodingType.SGTIN_96;
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
            String str = ((sEpcHeader + b.b(3, this.mEpcFilter)) + ((AbstractGs1GtinPaddedPartitionInputRecogniser) inputRecogniser()).partitionAsBinary()) + sZeroSerialNumber;
            this.mBinaryEpcEncoding = str;
            this.mEpcEncoding = b.c(24, str);
        }
    }
}
