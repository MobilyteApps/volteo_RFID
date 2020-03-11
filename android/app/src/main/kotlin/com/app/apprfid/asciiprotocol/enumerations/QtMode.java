package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class QtMode extends EnumerationBase {
    public static final QtMode ACCESS_CONTROL_WORD = new QtMode("3", "The value is specified as read or write the QT control word");
    public static final QtMode ACCESS_PRIVATE_MEMORY = new QtMode("1", "The value is specified as read or write the private memory");
    public static final QtMode ACCESS_PRIVATE_MEMORY_SHORT_RANGE = new QtMode("2", "The value is specified as read or write the private memory with short range");
    public static final QtMode NONE = new QtMode("0", "The value is specified as standard read/write");
    public static final QtMode NOT_SPECIFIED = null;
    public static final QtMode[] PRIVATE_VALUES = {NOT_SPECIFIED, NONE, ACCESS_PRIVATE_MEMORY, ACCESS_PRIVATE_MEMORY_SHORT_RANGE, ACCESS_CONTROL_WORD};
    private static HashMap parameterLookUp;

    private QtMode(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final QtMode Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (QtMode) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, QtMode.class.getName()}));
    }

    public static final QtMode[] getValues() {
        return PRIVATE_VALUES;
    }
}
