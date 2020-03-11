package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.TargetIdentifiers.IGs1CompanyPrefixProvider;
import com.app.apprfid.findmodel.TargetIdentifiers.IGs1CompanyPrefixProvider;
import com.app.apprfid.util.d;


public class Gs1GraiShortCodeInputRecogniser extends AbstractGs1GraiPaddedPartitionInputRecogniser {
    private boolean mIgnoreTrailingDigit;

    public Gs1GraiShortCodeInputRecogniser(IGs1CompanyPrefixProvider iGs1CompanyPrefixProvider) {
        super(iGs1CompanyPrefixProvider);
    }

    public Gs1GraiShortCodeInputRecogniser(IGs1CompanyPrefixProvider iGs1CompanyPrefixProvider, boolean z) {
        super(iGs1CompanyPrefixProvider);
        this.mIgnoreTrailingDigit = z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
    public boolean isRecognised(String str) {
        boolean z = true;
        if (this.mIgnoreTrailingDigit) {
            str = str.substring(0, str.length() - 1);
        }
        String str2 = "";
        if (d.a(str) && str.length() <= 6) {
            int length = 12 - this.mGs1CompanyPrefixProvider.gs1CompanyPrefix().length();
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
                this.mPaddedPartition = graiPartition(this.mGs1CompanyPrefixProvider.gs1CompanyPrefix(), str2);
            }
            return z;
        }
        z = false;
        if (z) {
        }
        return z;
    }
}
