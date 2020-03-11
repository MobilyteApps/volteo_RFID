package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.TargetIdentifiers.IGs1CompanyPrefixProvider;
import com.app.apprfid.util.b;


public abstract class AbstractGs1GraiPaddedPartitionInputRecogniser extends AbstractGs1PaddedPartitionInputRecogniser {
    public AbstractGs1GraiPaddedPartitionInputRecogniser(IGs1CompanyPrefixProvider iGs1CompanyPrefixProvider) {
        super(iGs1CompanyPrefixProvider);
    }

    public static String graiPartition(String str, String str2) {
        int[] iArr = {40, 37, 34, 30, 27, 24, 20};
        int[] iArr2 = {4, 7, 10, 14, 17, 20, 24};
        try {
            if (str.length() < 6 || str.length() > 12) {
                return null;
            }
            int length = 12 - str.length();
            String str3 = b.b(3, Integer.toString(length)) + b.b(iArr[length], str);
            if (str.length() + str2.length() == 12) {
                return str3 + b.b(iArr2[length], str2);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
