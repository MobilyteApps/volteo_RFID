package com.app.apprfid.findmodel.TargetIdentifiers;


import com.app.apprfid.findmodel.EncodingType;
import com.app.apprfid.util.g;


public class HexTargetIdentifier extends AbstractTargetIdentifier {
    public String description() {
        return "Hex EPC Identifier";
    }

    public EncodingType encodingType() {
        return EncodingType.HEX;
    }

    public String epcEncoding() {
        return value();
    }

    public int epcMatchingBitLength() {
        return epcEncoding().length() * 4;
    }

    public boolean isMatch(String str) {
        return str.startsWith(epcEncoding());
    }

    public boolean isValueValid() {
        boolean z = super.isValueValid() && g.b(this.mValue);
        String str = z ? "" : value().length() == 0 ? "Cannot be empty!" : "Contains non-Hex characters!";
        this.mValueValidityMessage = str;
        return z;
    }

    public void setValue(String str) {
        this.mValue = str == null ? "" : str.trim();
    }
}
