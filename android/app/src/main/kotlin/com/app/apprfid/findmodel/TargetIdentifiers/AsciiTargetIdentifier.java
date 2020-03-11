package com.app.apprfid.findmodel.TargetIdentifiers;

import com.app.apprfid.findmodel.EncodingType;
import com.app.apprfid.util.a;


import java.util.Locale;

public class AsciiTargetIdentifier extends AbstractTargetIdentifier {
    private String mEpcEncoding;

    public String description() {
        return "ASCII EPC Identifier";
    }

    public EncodingType encodingType() {
        return EncodingType.ASCII;
    }

    public String epcEncoding() {
        return this.mEpcEncoding;
    }

    public int epcMatchingBitLength() {
        return epcEncoding().length() * 4;
    }

    public boolean isMatch(String str) {
        return str.startsWith(epcEncoding());
    }

    public boolean isValueValid() {
        boolean isValueValid = super.isValueValid();
        String str = isValueValid ? "" : value().length() == 0 ? "Cannot be empty!" : "Contains non-ASCII characters!";
        this.mValueValidityMessage = str;
        return isValueValid;
    }

    public void setValue(String str) {
        super.setValue(str);
        this.mEpcEncoding = a.a(this.mValue).toUpperCase(Locale.US);
    }

    public String valueFromHexEpc(String str) {
        return a.b(str);
    }
}
