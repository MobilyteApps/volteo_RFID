package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class ChargeState extends EnumerationBase {
    public static final ChargeState CHARGING = new ChargeState("Charging", "The battery is charging");
    public static final ChargeState COMPLETE = new ChargeState("Complete", "Battery charging is complete");
    public static final ChargeState ERROR = new ChargeState("Error", "There is a battery charging error");
    public static final ChargeState NOT_SPECIFIED = null;
    public static final ChargeState OFF = new ChargeState("Off", "The battery is not being charged");
    public static final ChargeState[] PRIVATE_VALUES = {NOT_SPECIFIED, OFF, CHARGING, COMPLETE, ERROR};
    private static HashMap parameterLookUp;

    private ChargeState(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final ChargeState Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (ChargeState) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, ChargeState.class.getName()}));
    }

    public static final ChargeState[] getValues() {
        return PRIVATE_VALUES;
    }
}
