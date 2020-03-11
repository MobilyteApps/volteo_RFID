package com.app.apprfid.findmodel.TargetIdentifiers;


import com.app.apprfid.findmodel.InputRecognisers.AbstractGs1InputRecogniser;
import com.app.apprfid.util.b;
import com.app.apprfid.util.d;


public abstract class AbstractGs1TargetIdentifier extends AbstractTargetIdentifier implements IGs1CompanyPrefixProvider {
    public static final int COMPANY_PREFIX_MISSING = 1;
    public static final int COMPANY_PREFIX_VALID = 0;
    public static final int COMPANY_PREFIX_WRONG_LENGTH = 2;
    public static final int EPC_FILTER_INVALID = 1;
    public static final int EPC_FILTER_VALID = 0;
    protected String mBinaryEpcEncoding;
    protected String mEpcEncoding;
    protected String mEpcFilter = "-1";
    protected int mEpcFilterValidityCode = 1;
    protected String mGs1CompanyPrefix = "";
    protected int mGs1CompanyPrefixValidityCode = 1;
    private AbstractGs1InputRecogniser mInputRecogniser = null;
    protected AbstractGs1InputRecogniser[] mInputRecognisers;
    private boolean mIsValueValid = false;

    public static int epcFilterValidityCode(int i) {
        return (i < 0 || i > 7) ? 1 : 0;
    }

    public static int gs1CompanyPrefixValidityCode(String str) {
        String trim = str == null ? "" : str.trim();
        if (trim.length() == 0) {
            return 1;
        }
        return (trim.length() < 6 || trim.length() > 12) ? 2 : 0;
    }

    public static boolean isGs1CompanyPrefixValid(String str) {
        return gs1CompanyPrefixValidityCode(str) == 0;
    }

    public String epcFilter() {
        return this.mEpcFilter;
    }

    public int epcFilterValidityCode() {
        return this.mEpcFilterValidityCode;
    }

    public int epcMatchingBitLength() {
        return this.mBinaryEpcEncoding.length();
    }

    public String gs1CompanyPrefix() {
        return this.mGs1CompanyPrefix;
    }

    public int gs1CompanyPrefixValidityCode() {
        return this.mGs1CompanyPrefixValidityCode;
    }

    /* access modifiers changed from: protected */
    public AbstractGs1InputRecogniser inputRecogniser() {
        return this.mInputRecogniser;
    }

    public boolean isEpcFilterValid() {
        return this.mEpcFilterValidityCode == 0;
    }

    public boolean isGs1CompanyPrefixValid() {
        return this.mGs1CompanyPrefixValidityCode == 0;
    }

    public boolean isMatch(String str) {
        return b.d(str.length() * 4, str).substring(0, epcMatchingBitLength()).equals(this.mBinaryEpcEncoding.substring(0, epcMatchingBitLength()));
    }

    public boolean isValueValid() {
        return this.mIsValueValid;
    }

    public void setEpcFilter(String str) {
        this.mEpcFilter = str;
        this.mEpcFilterValidityCode = epcFilterValidityCode(Integer.parseInt(str));
    }

    public void setGs1CompanyPrefix(String str) {
        this.mGs1CompanyPrefix = str == null ? "" : str.trim();
        this.mEpcEncoding = "";
        this.mGs1CompanyPrefixValidityCode = gs1CompanyPrefixValidityCode(this.mGs1CompanyPrefix);
    }

    public void setValue(String str) {
        super.setValue(str);
        this.mBinaryEpcEncoding = "";
        this.mEpcEncoding = "";
        validateValue();
    }

    /* access modifiers changed from: protected */
    public void validateValue() {
        boolean z = false;
        this.mValueValidityMessage = "";
        this.mInputRecogniser = null;
        if (d.a(this.mValue) && super.isValueValid() && isGs1CompanyPrefixValid()) {
            this.mInputRecogniser = null;
            AbstractGs1InputRecogniser[] abstractGs1InputRecogniserArr = this.mInputRecognisers;
            int length = abstractGs1InputRecogniserArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                AbstractGs1InputRecogniser abstractGs1InputRecogniser = abstractGs1InputRecogniserArr[i];
                if (abstractGs1InputRecogniser.isRecognised(this.mValue)) {
                    z = true;
                    this.mInputRecogniser = abstractGs1InputRecogniser;
                    break;
                }
                i++;
            }
        }
        this.mIsValueValid = z;
        if (!z) {
            this.mValueValidityMessage = "Invalid identifier";
        }
    }
}
