package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class TransponderBackscatterErrorCode extends EnumerationBase {
    public static final TransponderBackscatterErrorCode GENERAL_ERROR = new TransponderBackscatterErrorCode("000", "General error.");
    public static final TransponderBackscatterErrorCode MEMORY_DOES_NOT_EXIST_OR_THE_PC_VALUE_IS_NOT_SUPPORTED = new TransponderBackscatterErrorCode("003", "Memory does not exist or the PC value is not supported.");
    public static final TransponderBackscatterErrorCode MEMORY_IS_LOCK_OR_PERMALOCKED = new TransponderBackscatterErrorCode("004", "Memory is lock or permalocked.");
    public static final TransponderBackscatterErrorCode NOT_SPECIFIED = null;
    public static final TransponderBackscatterErrorCode TRANSPONDER_DOES_NOT_SUPPORT_ERROR_SPECIFIC_CODES = new TransponderBackscatterErrorCode("015", "Transponder does not support error specific codes.");
    public static final TransponderBackscatterErrorCode TRANSPONDER_HAS_INSUFFICIENT_POWER = new TransponderBackscatterErrorCode("011", "Transponder has insufficient power.");
    public static final TransponderBackscatterErrorCode[] PRIVATE_VALUES = {NOT_SPECIFIED, GENERAL_ERROR, MEMORY_DOES_NOT_EXIST_OR_THE_PC_VALUE_IS_NOT_SUPPORTED, MEMORY_IS_LOCK_OR_PERMALOCKED, TRANSPONDER_HAS_INSUFFICIENT_POWER, TRANSPONDER_DOES_NOT_SUPPORT_ERROR_SPECIFIC_CODES};
    private static HashMap parameterLookUp;

    private TransponderBackscatterErrorCode(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final TransponderBackscatterErrorCode Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (TransponderBackscatterErrorCode) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, TransponderBackscatterErrorCode.class.getName()}));
    }

    public static final TransponderBackscatterErrorCode[] getValues() {
        return PRIVATE_VALUES;
    }
}
