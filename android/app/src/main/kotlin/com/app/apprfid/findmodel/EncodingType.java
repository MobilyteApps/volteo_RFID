package com.app.apprfid.findmodel;


import com.app.apprfid.util.f;

public class EncodingType extends f {
    public static final EncodingType ASCII = new EncodingType("ASCII", "ASCII Encoding");
    public static final EncodingType GRAI_96 = new EncodingType("GRAI-96", "GRAI-96 Encoding");
    public static final EncodingType HEX = new EncodingType("Hex", "Hex Encoding");
    public static final EncodingType SGTIN_96 = new EncodingType("SGTIN-96", "SGTIN-96 Encoding");
    private static final String[] PRIVATE_DESCRIPTIONS = {HEX.getDescription(), ASCII.getDescription(), SGTIN_96.getDescription(), GRAI_96.getDescription()};
    private static final EncodingType[] PRIVATE_VALUES = {HEX, ASCII, SGTIN_96, GRAI_96};

    public EncodingType(String str) {
        super(str);
    }

    public EncodingType(String str, String str2) {
        super(str, str2);
    }

    public static final String[] getDescriptions() {
        return PRIVATE_DESCRIPTIONS;
    }

    public static final EncodingType[] getValues() {
        return PRIVATE_VALUES;
    }

    public static final EncodingType typeFromDescription(String str) {
        EncodingType[] encodingTypeArr;
        for (EncodingType encodingType : PRIVATE_VALUES) {
            if (encodingType.getDescription().equals(str)) {
                return encodingType;
            }
        }
        return null;
    }
}
