package com.app.apprfid.findmodel;


import com.app.apprfid.util.f;

public class SearchType extends f {
    public static final SearchType FEW = new SearchType("A few matching tags");
    public static final SearchType MANY = new SearchType("Many matching tags");
    public static final SearchType UNIQUE = new SearchType("A uniquely matched tag");
    private static final SearchType[] PRIVATE_VALUES = {UNIQUE, FEW, MANY};
    private int mIndex = -1;

    public SearchType(String str) {
        super(str);
    }

    public static int getIndex(SearchType searchType) {
        int i = 0;
        while (i < PRIVATE_VALUES.length && PRIVATE_VALUES[i] != searchType) {
            i++;
        }
        return i;
    }

    public static SearchType getValueForIndex(int i) {
        if (i < 0 || i >= PRIVATE_VALUES.length) {
            return null;
        }
        return PRIVATE_VALUES[i];
    }

    public static final SearchType[] getValues() {
        return PRIVATE_VALUES;
    }

    public int getIndex() {
        if (this.mIndex < 0) {
            this.mIndex = getIndex(this);
        }
        return this.mIndex;
    }
}
