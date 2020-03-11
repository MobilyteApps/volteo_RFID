package com.app.apprfid.findmodel.TargetIdentifiers;

import android.util.Log;

import com.app.apprfid.findmodel.EncodingType;

public class TargetIdentifierFactory {
    public static final TargetIdentifierFactory sharedFactory = new TargetIdentifierFactory();

    public static ITargetIdentifier createInstance(EncodingType encodingType) {
        if (encodingType.equals(EncodingType.HEX)) {
            return new HexTargetIdentifier();
        }
        if (encodingType.equals(EncodingType.ASCII)) {
            return new AsciiTargetIdentifier();
        }
        if (encodingType.equals(EncodingType.SGTIN_96)) {
            return new Sgtin96TargetIdentifier();
        }
        if (encodingType.equals(EncodingType.GRAI_96)) {
            return new Grai96TargetIdentifier();
        }
        Log.w("TargetIdentifierFactory", "Invalid Encoding Type - defaulted to Hex");
        return new HexTargetIdentifier();
    }
}
