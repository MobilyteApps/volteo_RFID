package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class QueryTarget extends EnumerationBase {
    public static final QueryTarget NOT_SPECIFIED = null;
    public static final QueryTarget TARGET_A = new QueryTarget("a", "Select target A");
    public static final QueryTarget TARGET_B = new QueryTarget("b", "Select target B");
    private static HashMap parameterLookUp;
    public static final QueryTarget[] PRIVATE_VALUES = {NOT_SPECIFIED, TARGET_A, TARGET_B};

    private QueryTarget(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final QueryTarget Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (QueryTarget) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, QueryTarget.class.getName()}));
    }

    public static final QueryTarget[] getValues() {
        return PRIVATE_VALUES;
    }
}
