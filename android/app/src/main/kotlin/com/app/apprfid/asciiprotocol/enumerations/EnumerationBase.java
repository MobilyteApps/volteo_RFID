package com.app.apprfid.asciiprotocol.enumerations;

public abstract class EnumerationBase {
    private String mArgument;
    private String mDescription;

    protected EnumerationBase(String str, String str2) {
        setArgument(str);
        setDescription(str2);
    }

    private void setArgument(String str) {
        this.mArgument = str;
    }

    private void setDescription(String str) {
        this.mDescription = str;
    }

    public String getArgument() {
        return this.mArgument;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String toString() {
        return getClass().getName() + ": " + this.mArgument + " (" + this.mDescription + ")";
    }
}
