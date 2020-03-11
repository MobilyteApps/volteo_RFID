package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.TargetIdentifiers.IGs1CompanyPrefixProvider;
import com.app.apprfid.util.c;
import com.app.apprfid.util.d;

public class Gs1GtinRcnInputRecogniser extends AbstractGs1GtinPaddedPartitionInputRecogniser {
    public Gs1GtinRcnInputRecogniser(IGs1CompanyPrefixProvider iGs1CompanyPrefixProvider) {
        super(iGs1CompanyPrefixProvider);
    }

    public boolean isRecognised(String str) {
        boolean z = false;
        String str2 = "";
        if (d.a(str) && str.length() == 8) {
            String substring = str.substring(0, str.length() - 1);
            String substring2 = str.substring(str.length() - 1, str.length());
            String a = c.a(substring);
            if (a != null && a.equals(substring2)) {
                String substring3 = str.substring(0, 1);
                if (substring3.equals("0") || substring3.equals("2")) {
                    String substring4 = substring.substring(1);
                    int length = (13 - this.mGs1CompanyPrefixProvider.gs1CompanyPrefix().length()) - 1;
                    if (length == substring4.length()) {
                        str2 = substring4;
                        z = true;
                    } else if (substring4.length() > length) {
                        int length2 = substring4.length() - length;
                        if (substring4.substring(0, length2).equals("000000".substring(length))) {
                            str2 = substring4.substring(length2);
                            z = true;
                        }
                    }
                }
            }
            if (z) {
                this.mPaddedPartition = gtinPartition(this.mGs1CompanyPrefixProvider.gs1CompanyPrefix(), "0", str2);
            }
        }
        return z;
    }
}
