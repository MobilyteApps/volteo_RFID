package com.app.apprfid.findmodel;


import com.app.apprfid.util.f;

public class ScanState extends f {
    public static final ScanState SCANNING = new ScanState("The FindModel is scanning");
    public static final ScanState STOPPED = new ScanState("The FindModel is stopped");
    public static final ScanState UNKNOWN = null;
    public static final ScanState[] PRIVATE_VALUES = {UNKNOWN, STOPPED, SCANNING};

    public ScanState(String str) {
        super(str);
    }

    public static final ScanState[] getValues() {
        return PRIVATE_VALUES;
    }
}
