package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class TransponderAccessErrorCode extends EnumerationBase {
    public static final TransponderAccessErrorCode COMMAND_FORMAT_ERROR = new TransponderAccessErrorCode("007", "Command format error.");
    public static final TransponderAccessErrorCode CRC_ERROR_ON_TRANSPONDER_RESPONSE = new TransponderAccessErrorCode("002", "CRC error on transponder response.");
    public static final TransponderAccessErrorCode HANDLE_MISMATCH = new TransponderAccessErrorCode("001", "Handle mismatch.");
    public static final TransponderAccessErrorCode INVALID_PASSWORD = new TransponderAccessErrorCode("004", "Invalid password.");
    public static final TransponderAccessErrorCode NOT_SPECIFIED = null;
    public static final TransponderAccessErrorCode NO_TRANSPONDER_REPLY = new TransponderAccessErrorCode("003", "No transponder reply.");
    public static final TransponderAccessErrorCode OPERATION_FAILED = new TransponderAccessErrorCode("255", "Operation failed.");
    public static final TransponderAccessErrorCode OUT_OF_RETRIES = new TransponderAccessErrorCode("009", "Out of retries.");
    public static final TransponderAccessErrorCode READ_COUNT_INVALID = new TransponderAccessErrorCode("008", "Read count invalid.");
    public static final TransponderAccessErrorCode TRANSPONDER_LOST = new TransponderAccessErrorCode("006", "Transponder lost.");
    public static final TransponderAccessErrorCode ZERO_KILL_PASSWORD = new TransponderAccessErrorCode("005", "Zero kill password.");
    public static final TransponderAccessErrorCode[] PRIVATE_VALUES = {NOT_SPECIFIED, HANDLE_MISMATCH, CRC_ERROR_ON_TRANSPONDER_RESPONSE, NO_TRANSPONDER_REPLY, INVALID_PASSWORD, ZERO_KILL_PASSWORD, TRANSPONDER_LOST, COMMAND_FORMAT_ERROR, READ_COUNT_INVALID, OUT_OF_RETRIES, OPERATION_FAILED};
    private static HashMap parameterLookUp;

    private TransponderAccessErrorCode(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final TransponderAccessErrorCode Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (TransponderAccessErrorCode) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, TransponderAccessErrorCode.class.getName()}));
    }

    public static final TransponderAccessErrorCode[] getValues() {
        return PRIVATE_VALUES;
    }
}
