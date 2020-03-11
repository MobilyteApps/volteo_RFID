package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.TargetIdentifiers.IGs1CompanyPrefixProvider;
import com.app.apprfid.util.c;
import com.app.apprfid.util.d;

public class Gs1GtinInputRecogniser extends AbstractGs1GtinPaddedPartitionInputRecogniser implements IGs1PaddedPartitionProvider {
    public Gs1GtinInputRecogniser(IGs1CompanyPrefixProvider iGs1CompanyPrefixProvider) {
        super(iGs1CompanyPrefixProvider);
    }

    public boolean isRecognised(String str) {
        boolean z;
        if (!d.a(str)) {
            return false;
        }
        String substring = str.substring(0, str.length() - 1);
        String substring2 = str.substring(str.length() - 1, str.length());
        String a = c.a(substring);
        if (a == null || !a.equals(substring2)) {
            z = false;
        } else if (str.length() == 8) {
            String substring3 = str.substring(0, 1);
            z = !substring3.equals("0") && !substring3.equals("2");
            if (z) {
                this.mPaddedPartition = gtinPartition("00000" + str.substring(0, 3), "0", str.substring(3, 7));
            }
        } else {
            if (str.length() != 14) {
                str = "000000".substring(0, 14 - str.length()) + str;
            }
            z = str.startsWith(this.mGs1CompanyPrefixProvider.gs1CompanyPrefix(), 1);
            if (z) {
                String gs1CompanyPrefix = this.mGs1CompanyPrefixProvider.gs1CompanyPrefix();
                this.mPaddedPartition = gtinPartition(gs1CompanyPrefix, str.substring(0, 1), str.substring(gs1CompanyPrefix.length() + 1, 13));
            }
        }
        return z;
    }
}
