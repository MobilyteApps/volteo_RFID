package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class BuzzerTone extends EnumerationBase {
    public static final BuzzerTone HIGH = new BuzzerTone("hig", "A high pitch tone");
    public static final BuzzerTone LOW = new BuzzerTone("low", "A low pitch tone");
    public static final BuzzerTone MEDIUM = new BuzzerTone("med", "A medium pitch tone");
    public static final BuzzerTone NOT_SPECIFIED = null;
    public static final BuzzerTone[] PRIVATE_VALUES = {NOT_SPECIFIED, LOW, MEDIUM, HIGH};
    private static HashMap parameterLookUp;

    private BuzzerTone(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final BuzzerTone Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (BuzzerTone) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, BuzzerTone.class.getName()}));
    }

    public static final BuzzerTone[] getValues() {
        return PRIVATE_VALUES;
    }
}
