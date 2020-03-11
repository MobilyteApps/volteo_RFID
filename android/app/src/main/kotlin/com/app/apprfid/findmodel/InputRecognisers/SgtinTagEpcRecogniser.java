package com.app.apprfid.findmodel.InputRecognisers;

import com.app.apprfid.findmodel.EncodingType;
import com.app.apprfid.findmodel.SearchType;
import com.app.apprfid.findmodel.TargetIdentifiers.IGs1CompanyPrefixProvider;
import com.app.apprfid.findmodel.TargetIdentifiers.IGs1SgtinTagEpcInformationProvider;
import com.app.apprfid.util.b;


public class SgtinTagEpcRecogniser extends AbstractGs1TagEpcRecogniser implements IGs1CompanyPrefixProvider,
        IGs1SgtinTagEpcInformationProvider {
    protected EncodingType mEncodingType;
    protected String mEpcHeader;
    protected String mGs1CompanyPrefix;
    protected String mIndicatorDigit;
    protected String mItemReference;

    public SgtinTagEpcRecogniser() {
        reset();
    }

    private void reset() {
        this.mEncodingType = null;
        this.mEpcHeader = "";
        this.mEpcFilterValue = "";
        this.mIndicatorDigit = "";
        this.mGs1CompanyPrefix = "";
        this.mItemReference = "";
    }

    public SearchType defaultSearchType() {
        return SearchType.FEW;
    }

    public EncodingType encodingType() {
        return this.mEncodingType;
    }

    public String epcHeader() {
        return this.mEpcHeader;
    }

    public String gs1CompanyPrefix() {
        return this.mGs1CompanyPrefix;
    }

    public String indicatorDigit() {
        return this.mIndicatorDigit;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0082  */
    public boolean isRecognised(String str) {
        boolean z = true;
        this.mEpcHeader = str.substring(0, 2);
        String str2 = null;
        if (str.length() == 24 && this.mEpcHeader.equals("30")) {
            str2 = b.d(96, str);
            this.mEncodingType = EncodingType.SGTIN_96;
        }
        if (str2 != null) {
            this.mEpcFilterValue = b.a(1, str2.substring(8, 11));
            int a = b.a(str2.substring(11, 14));
            if (a != 7) {
                int[] iArr = {12, 11, 10, 9, 8, 7, 6};
                int[] iArr2 = {1, 2, 3, 4, 5, 6, 7};
                int i = new int[]{40, 37, 34, 30, 27, 24, 20}[a] + 14;
                int i2 = new int[]{4, 7, 10, 14, 17, 20, 24}[a] + i;
                this.mGs1CompanyPrefix = b.a(iArr[a], str2.substring(14, i));
                String a2 = b.a(iArr2[a], str2.substring(i, i2));
                this.mIndicatorDigit = a2.substring(0, 1);
                this.mItemReference = a2.substring(1);
                if (!z) {
                    reset();
                }
                return z;
            }
        }
        z = false;
        if (!z) {
        }
        return z;
    }

    public String itemReference() {
        return this.mItemReference;
    }
}
