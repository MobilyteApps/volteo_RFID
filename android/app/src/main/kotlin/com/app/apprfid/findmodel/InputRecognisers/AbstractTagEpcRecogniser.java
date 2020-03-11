package com.app.apprfid.findmodel.InputRecognisers;


import com.app.apprfid.findmodel.EncodingType;
import com.app.apprfid.findmodel.SearchType;

public abstract class AbstractTagEpcRecogniser implements IInputRecogniser {
    public abstract SearchType defaultSearchType();

    public abstract EncodingType encodingType();
}
