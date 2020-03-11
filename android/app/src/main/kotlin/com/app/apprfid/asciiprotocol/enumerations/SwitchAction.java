package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class SwitchAction extends EnumerationBase {
    public static final SwitchAction ANTENNA_DEFAULT = new SwitchAction("ad", "Antenna Default");
    public static final SwitchAction BARCODE = new SwitchAction("bar", "Barcode - perform the command with the last specified parameters");
    public static final SwitchAction INVENTORY = new SwitchAction("inv", "Inventory - perform the command with the last specified parameters");
    public static final SwitchAction NO_CHANGE = null;
    public static final SwitchAction OFF = new SwitchAction("off", "Off - no action");
    public static final SwitchAction READ = new SwitchAction("rd", "Read - perform the command with the last specified parameters");
    public static final SwitchAction USER = new SwitchAction("usr", "Perform the user specified command");
    public static final SwitchAction WRITE = new SwitchAction("wr", "Write - perform the command with the last specified parameters");
    public static final SwitchAction[] PRIVATE_VALUES = {NO_CHANGE, OFF, READ, WRITE, INVENTORY, BARCODE, USER, ANTENNA_DEFAULT};
    private static HashMap parameterLookUp;

    private SwitchAction(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final SwitchAction Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (SwitchAction) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, SwitchAction.class.getName()}));
    }

    public static final SwitchAction[] getValues() {
        return PRIVATE_VALUES;
    }
}
