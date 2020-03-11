package com.app.apprfid.findmodel.TargetIdentifiers;

public abstract class AbstractTargetIdentifier implements ITargetIdentifier {
    protected String mValue;
    protected String mValueValidityMessage = "";

    public boolean isValueValid() {
        return this.mValue != null && this.mValue.length() > 0;
    }

    public void setValue(String str) {
        if (str == null) {
            str = "";
        }
        this.mValue = str;
    }

    public String value() {
        return this.mValue;
    }

    public String valueFromHexEpc(String str) {
        return str;
    }

    public String valueValidityMessage() {
        return this.mValueValidityMessage;
    }
}
