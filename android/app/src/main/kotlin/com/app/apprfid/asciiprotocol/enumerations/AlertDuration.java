package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class AlertDuration extends EnumerationBase {
    public static final AlertDuration LONG = new AlertDuration("lon", "A long duration");
    public static final AlertDuration MEDIUM = new AlertDuration("med", "A medium duration ");
    public static final AlertDuration NOT_SPECIFIED = null;
    public static final AlertDuration SHORT = new AlertDuration("sho", "A short duration");
    public static final AlertDuration[] PRIVATE_VALUES = {NOT_SPECIFIED, SHORT, MEDIUM, LONG};
    private static HashMap parameterLookUp;

    private AlertDuration(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final AlertDuration Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (AlertDuration) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, AlertDuration.class.getName()}));
    }

    public static final AlertDuration[] getValues() {
        return PRIVATE_VALUES;
    }
}
