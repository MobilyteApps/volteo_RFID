package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class TriState extends EnumerationBase {
    public static final TriState NO = new TriState("off", "The value is specified off");
    public static final TriState NOT_SPECIFIED = null;
    public static final TriState YES = new TriState("on", "The value is specified on");
    public static final TriState[] PRIVATE_VALUES = {NOT_SPECIFIED, YES, NO};
    private static HashMap parameterLookUp;

    private TriState(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final TriState Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (TriState) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, TriState.class.getName()}));
    }

    public static final TriState[] getValues() {
        return PRIVATE_VALUES;
    }
}
