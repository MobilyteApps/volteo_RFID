package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class QuerySession extends EnumerationBase {
    public static final QuerySession NOT_SPECIFIED = null;
    public static final QuerySession SESSION_0 = new QuerySession("s0", "Target session 0");
    public static final QuerySession SESSION_1 = new QuerySession("s1", "Target session 1");
    public static final QuerySession SESSION_2 = new QuerySession("s2", "Target session 2");
    public static final QuerySession SESSION_3 = new QuerySession("s3", "Target session 3");
    public static final QuerySession[] PRIVATE_VALUES = {NOT_SPECIFIED, SESSION_0, SESSION_1, SESSION_2, SESSION_3};
    private static HashMap parameterLookUp;

    private QuerySession(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final QuerySession Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (QuerySession) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, QuerySession.class.getName()}));
    }

    public static final QuerySession[] getValues() {
        return PRIVATE_VALUES;
    }
}
