package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class DeleteConfirmation extends EnumerationBase {
    public static final DeleteConfirmation NOT_SPECIFIED = null;
    public static final DeleteConfirmation YES = new DeleteConfirmation("yes", "The value is specified yes");
    public static final DeleteConfirmation[] PRIVATE_VALUES = {NOT_SPECIFIED, YES};
    private static HashMap parameterLookUp;

    private DeleteConfirmation(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final DeleteConfirmation Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (DeleteConfirmation) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, DeleteConfirmation.class.getName()}));
    }

    public static final DeleteConfirmation[] getValues() {
        return PRIVATE_VALUES;
    }
}
