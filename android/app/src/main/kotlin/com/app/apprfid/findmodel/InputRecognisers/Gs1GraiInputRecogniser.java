package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.TargetIdentifiers.IGs1CompanyPrefixProvider;
import com.app.apprfid.util.c;
import com.app.apprfid.util.d;

public class Gs1GraiInputRecogniser extends AbstractGs1GraiPaddedPartitionInputRecogniser {
    public Gs1GraiInputRecogniser(IGs1CompanyPrefixProvider iGs1CompanyPrefixProvider) {
        super(iGs1CompanyPrefixProvider);
    }

    public boolean isRecognised(String str) {
        boolean z = false;
        if (d.a(str) && str.length() >= 18 && str.substring(0, 5).equals("80030")) {
            String substring = str.substring(4, 17);
            String substring2 = str.substring(17, 18);
            String b = c.b(substring);
            if (b != null && b.equals(substring2)) {
                String substring3 = str.substring(4, 18);
                z = substring3.startsWith(this.mGs1CompanyPrefixProvider.gs1CompanyPrefix(), 1);
                if (z) {
                    String gs1CompanyPrefix = this.mGs1CompanyPrefixProvider.gs1CompanyPrefix();
                    this.mPaddedPartition = graiPartition(gs1CompanyPrefix, substring3.substring(gs1CompanyPrefix.length() + 1, 13));
                }
            }
        }
        return z;
    }
}
