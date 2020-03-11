package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class QAlgorithm extends EnumerationBase {
    public static final QAlgorithm DYNAMIC = new QAlgorithm("dyn", "Dynamic time-slots has been specified");
    public static final QAlgorithm FIXED = new QAlgorithm("fix", "Fixed time-slots has been specified");
    public static final QAlgorithm NOT_SPECIFIED = null;
    public static final QAlgorithm[] PRIVATE_VALUES = {NOT_SPECIFIED, FIXED, DYNAMIC};
    private static HashMap parameterLookUp;

    private QAlgorithm(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final QAlgorithm Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (QAlgorithm) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, QAlgorithm.class.getName()}));
    }

    public static final QAlgorithm[] getValues() {
        return PRIVATE_VALUES;
    }
}
