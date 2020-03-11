package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class ImpinjBlockWriteMode extends EnumerationBase {
    public static final ImpinjBlockWriteMode AUTO = new ImpinjBlockWriteMode("a", "The value is specified as Auto");
    public static final ImpinjBlockWriteMode FORCE_1 = new ImpinjBlockWriteMode("1", "The value is specified as Force 1");
    public static final ImpinjBlockWriteMode FORCE_2 = new ImpinjBlockWriteMode("2", "The value is specified as Force 2");
    public static final ImpinjBlockWriteMode NOT_SPECIFIED = null;
    public static final ImpinjBlockWriteMode[] PRIVATE_VALUES = {NOT_SPECIFIED, AUTO, FORCE_1, FORCE_2};
    private static HashMap parameterLookUp;

    private ImpinjBlockWriteMode(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final ImpinjBlockWriteMode Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (ImpinjBlockWriteMode) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, ImpinjBlockWriteMode.class.getName()}));
    }

    public static final ImpinjBlockWriteMode[] getValues() {
        return PRIVATE_VALUES;
    }
}
