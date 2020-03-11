package com.app.apprfid.findmodel.TargetIdentifiers;


import com.app.apprfid.findmodel.EncodingType;

public interface ITargetIdentifier {
    String description();

    EncodingType encodingType();

    String epcEncoding();

    int epcMatchingBitLength();

    boolean isMatch(String str);

    boolean isValueValid();

    void setValue(String str);

    String value();

    String valueFromHexEpc(String str);

    String valueValidityMessage();
}
