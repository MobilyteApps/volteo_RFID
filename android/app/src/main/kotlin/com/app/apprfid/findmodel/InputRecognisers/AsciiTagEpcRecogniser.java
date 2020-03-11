package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.EncodingType;
import com.app.apprfid.findmodel.SearchType;
import com.app.apprfid.util.a;


public class AsciiTagEpcRecogniser extends AbstractTagEpcRecogniser {
    public SearchType defaultSearchType() {
        return SearchType.FEW;
    }

    public EncodingType encodingType() {
        return EncodingType.ASCII;
    }

    public boolean isRecognised(String str) {
        return str.length() % 2 == 0 && a.b(str) != null;
    }
}
