package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class SelectTarget extends EnumerationBase {
    public static final SelectTarget NOT_SPECIFIED = null;
    public static final SelectTarget SELECTED = new SelectTarget("sl", "Use select flag");
    public static final SelectTarget SESSION_0 = new SelectTarget("s0", "Use session 0");
    public static final SelectTarget SESSION_1 = new SelectTarget("s1", "Use session 1");
    public static final SelectTarget SESSION_2 = new SelectTarget("s2", "Use session 2");
    public static final SelectTarget SESSION_3 = new SelectTarget("s3", "Use session 3");
    public static final SelectTarget[] PRIVATE_VALUES = {NOT_SPECIFIED, SESSION_0, SESSION_1, SESSION_2, SESSION_3, SELECTED};
    private static HashMap parameterLookUp;

    private SelectTarget(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final SelectTarget Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (SelectTarget) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, SelectTarget.class.getName()}));
    }

    public static final SelectTarget[] getValues() {
        return PRIVATE_VALUES;
    }
}
