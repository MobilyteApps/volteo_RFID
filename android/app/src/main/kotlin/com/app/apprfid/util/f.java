package com.app.apprfid.util;

public abstract class f {
    private String mDescription;
    private String mDetailedDescription;

    protected f(String str) {
        setDescription(str);
        setDetailedDescription("");
    }

    protected f(String str, String str2) {
        setDescription(str);
        setDetailedDescription(str2);
    }

    private void setDescription(String str) {
        this.mDescription = str;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getDetailedDescription() {
        return this.mDetailedDescription;
    }

    /* access modifiers changed from: protected */
    public void setDetailedDescription(String str) {
        this.mDetailedDescription = str;
    }

    public String toString() {
        return getClass().getName() + ": " + this.mDescription;
    }
}
