package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.TargetIdentifiers.IGs1CompanyPrefixProvider;
import com.app.apprfid.util.d;

public class Gs1GtinShortCodeInputRecogniser extends AbstractGs1GtinPaddedPartitionInputRecogniser {
    private boolean mIgnoreTrailingDigit;

    public Gs1GtinShortCodeInputRecogniser(IGs1CompanyPrefixProvider iGs1CompanyPrefixProvider) {
        this(iGs1CompanyPrefixProvider, false);
    }

    public Gs1GtinShortCodeInputRecogniser(IGs1CompanyPrefixProvider iGs1CompanyPrefixProvider, boolean z) {
        super(iGs1CompanyPrefixProvider);
        this.mIgnoreTrailingDigit = z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0036  */
    public boolean isRecognised(String str) {
        boolean z = true;
        if (this.mIgnoreTrailingDigit) {
            str = str.substring(0, str.length() - 1);
        }
        String str2 = "";
        if (d.a(str) && str.length() <= 6) {
            int length = (13 - this.mGs1CompanyPrefixProvider.gs1CompanyPrefix().length()) - 1;
            if (length == str.length()) {
                str2 = str;
            } else if (str.length() > length) {
                int length2 = str.length() - length;
                if (str.substring(0, length2).equals("000000".substring(length))) {
                    str2 = str.substring(length2);
                }
            } else {
                str2 = "000000".substring(0, length - str.length()) + str;
            }
            if (z) {
                this.mPaddedPartition = gtinPartition(this.mGs1CompanyPrefixProvider.gs1CompanyPrefix(), "0", str2);
            }
            return z;
        }
        z = false;
        if (z) {
        }
        return z;
    }
}
