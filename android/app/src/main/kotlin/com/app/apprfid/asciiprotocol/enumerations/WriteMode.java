package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class WriteMode extends EnumerationBase {
    public static final WriteMode BLOCK = new WriteMode("b", "The value is specified as Block");
    public static final WriteMode NOT_SPECIFIED = null;
    public static final WriteMode SINGLE = new WriteMode("s", "The value is specified as Single");
    private static HashMap parameterLookUp;
    public static final WriteMode[] PRIVATE_VALUES = {NOT_SPECIFIED, SINGLE, BLOCK};

    private WriteMode(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final WriteMode Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (WriteMode) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, WriteMode.class.getName()}));
    }

    public static final WriteMode[] getValues() {
        return PRIVATE_VALUES;
    }
}
