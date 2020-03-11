package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class SwitchState extends EnumerationBase {
    public static final SwitchState DOUBLE = new SwitchState("double", "Switch is ON in double press");
    public static final SwitchState NOT_SPECIFIED = null;
    public static final SwitchState OFF = new SwitchState("off", "Switch is OFF / not pressed");
    public static final SwitchState SINGLE = new SwitchState("single", "Switch is ON in single press");
    public static final SwitchState[] PRIVATE_VALUES = {NOT_SPECIFIED, OFF, SINGLE, DOUBLE};
    private static HashMap parameterLookUp;

    private SwitchState(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final SwitchState Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (SwitchState) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, SwitchState.class.getName()}));
    }

    public static final SwitchState[] getValues() {
        return PRIVATE_VALUES;
    }
}
