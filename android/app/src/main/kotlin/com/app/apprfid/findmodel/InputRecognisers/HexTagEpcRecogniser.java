package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.EncodingType;
import com.app.apprfid.findmodel.SearchType;
import com.app.apprfid.util.g;
import com.app.apprfid.util.g;

public class HexTagEpcRecogniser extends AbstractTagEpcRecogniser {
    public SearchType defaultSearchType() {
        return SearchType.UNIQUE;
    }

    public EncodingType encodingType() {
        return EncodingType.HEX;
    }

    public boolean isRecognised(String str) {
        return g.a(str);
    }
}
