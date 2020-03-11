package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.TargetIdentifiers.IGs1CompanyPrefixProvider;
import com.app.apprfid.util.b;


public abstract class AbstractGs1GtinPaddedPartitionInputRecogniser extends AbstractGs1PaddedPartitionInputRecogniser {
    public AbstractGs1GtinPaddedPartitionInputRecogniser(IGs1CompanyPrefixProvider iGs1CompanyPrefixProvider) {
        super(iGs1CompanyPrefixProvider);
    }

    public static String gtinPartition(String str, String str2, String str3) {
        int[] iArr = {40, 37, 34, 30, 27, 24, 20};
        int[] iArr2 = {4, 7, 10, 14, 17, 20, 24};
        try {
            if (str.length() < 6 || str.length() > 12) {
                return null;
            }
            int length = 12 - str.length();
            String str4 = b.b(3, Integer.toString(length)) + b.b(iArr[length], str);
            String str5 = str2 + str3;
            if (str.length() + str5.length() == 13) {
                return str4 + b.b(iArr2[length], str5);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
