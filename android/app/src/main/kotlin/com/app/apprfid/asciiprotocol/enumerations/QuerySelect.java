package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class QuerySelect extends EnumerationBase {
    public static final QuerySelect ALL = new QuerySelect("all", "All (selected and not selected transponders)");
    public static final QuerySelect NOT_SELECTED = new QuerySelect("nsl", "Not selected transponders only");
    public static final QuerySelect NOT_SPECIFIED = null;
    public static final QuerySelect SELECTED = new QuerySelect("sl", "Selected transponders only");
    public static final QuerySelect[] PRIVATE_VALUES = {NOT_SPECIFIED, ALL, NOT_SELECTED, SELECTED};
    private static HashMap parameterLookUp;

    private QuerySelect(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final QuerySelect Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (QuerySelect) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, QuerySelect.class.getName()}));
    }

    public static final QuerySelect[] getValues() {
        return PRIVATE_VALUES;
    }
}
