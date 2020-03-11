package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class Databank extends EnumerationBase {
    public static final Databank ELECTRONIC_PRODUCT_CODE = new Databank("epc", "The EPC databank");
    public static final Databank NOT_SPECIFIED = null;
    public static final Databank RESERVED = new Databank("res", "The Reserved databank");
    public static final Databank TRANSPONDER_IDENTIFIER = new Databank("tid", "The TID databank");
    public static final Databank USER = new Databank("usr", "The User databank");
    public static final Databank[] PRIVATE_VALUES = {NOT_SPECIFIED, ELECTRONIC_PRODUCT_CODE, TRANSPONDER_IDENTIFIER, USER, RESERVED};
    private static HashMap parameterLookUp;

    private Databank(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final Databank Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (Databank) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, Databank.class.getName()}));
    }

    public static final Databank[] getValues() {
        return PRIVATE_VALUES;
    }
}
