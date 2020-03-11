package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.EncodingType;
import com.app.apprfid.findmodel.SearchType;
import com.app.apprfid.findmodel.TargetIdentifiers.IGs1CompanyPrefixProvider;
import com.app.apprfid.findmodel.TargetIdentifiers.IGs1TagEpcInformationProvider;
import com.app.apprfid.util.b;

public class GraiTagEpcRecogniser extends AbstractGs1TagEpcRecogniser implements IGs1CompanyPrefixProvider, IGs1TagEpcInformationProvider {
    protected String mAssetType;
    protected EncodingType mEncodingType;
    protected String mEpcHeader;
    protected String mGs1CompanyPrefix;

    public GraiTagEpcRecogniser() {
        reset();
    }

    private void reset() {
        this.mEncodingType = null;
        this.mEpcHeader = "";
        this.mEpcFilterValue = "";
        this.mGs1CompanyPrefix = "";
        this.mAssetType = "";
    }

    public String assetType() {
        return this.mAssetType;
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

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0074  */
    public boolean isRecognised(String str) {
        boolean z = true;
        this.mEpcHeader = str.substring(0, 2);
        String str2 = null;
        if (str.length() == 24 && this.mEpcHeader.equals("33")) {
            str2 = b.d(96, str);
            this.mEncodingType = EncodingType.GRAI_96;
        }
        if (str2 != null) {
            this.mEpcFilterValue = b.a(1, str2.substring(8, 11));
            int a = b.a(str2.substring(11, 14));
            if (a != 7) {
                int[] iArr = {12, 11, 10, 9, 8, 7, 6};
                int[] iArr2 = {0, 1, 2, 3, 4, 5, 6};
                int i = new int[]{40, 37, 34, 30, 27, 24, 20}[a] + 14;
                int i2 = new int[]{4, 7, 10, 14, 17, 20, 24}[a] + i;
                this.mGs1CompanyPrefix = b.a(iArr[a], str2.substring(14, i));
                int i3 = iArr2[a];
                if (i3 == 0) {
                    this.mAssetType = "";
                } else {
                    this.mAssetType = b.a(i3, str2.substring(i, i2));
                }
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
}
