package com.app.apprfid.asciiprotocol.enumerations;

import java.util.HashMap;

public class SelectAction extends EnumerationBase {
    public static final SelectAction ASSERT_SET_A_NOT_DEASSERT_SET_B = new SelectAction("0", "Match: Assert Select / Set Session A  Non Match: Deassert Select / Set Session B");
    public static final SelectAction ASSERT_SET_A_NOT_NOTHING_NOTHING = new SelectAction("1", "Match: Assert Select / Set Session A  Non Match: Nothing / Nothing");
    public static final SelectAction DEASSERT_SET_B_NOT_ASSERT_SET_A = new SelectAction("4", "Match: Deassert Select / Set Session B  Non Match: Assert Select / Set Session A");
    public static final SelectAction DEASSERT_SET_B_NOT_NOTHING_NOTHING = new SelectAction("5", "Match: Deassert Select / Set Session B  Non Match: Nothing / Nothing");
    public static final SelectAction NOTHING_NOTHING_NOT_ASSERT_SET_A = new SelectAction("6", "Match: Nothing / Nothing  Non Match: Assert Select / Set Session A");
    public static final SelectAction NOTHING_NOTHING_NOT_DEASSERT_SET_B = new SelectAction("2", "Match: Nothing / Nothing  Non Match: Deassert Select / Set Session B");
    public static final SelectAction NOTHING_NOTHING_NOT_TOGGLE_TOGGLE = new SelectAction("7", "Match: Nothing / Nothing  Non Match: Toggle / Toggle");
    public static final SelectAction NOT_SPECIFIED = null;
    public static final SelectAction TOGGLE_TOGGLE_NOT_NOTHING_NOTHING = new SelectAction("3", "Match: Toggle / Toggle  Non Match: Nothing / Nothing");
    public static final SelectAction[] PRIVATE_VALUES = {NOT_SPECIFIED, ASSERT_SET_A_NOT_DEASSERT_SET_B, ASSERT_SET_A_NOT_NOTHING_NOTHING, NOTHING_NOTHING_NOT_DEASSERT_SET_B, TOGGLE_TOGGLE_NOT_NOTHING_NOTHING, DEASSERT_SET_B_NOT_ASSERT_SET_A, DEASSERT_SET_B_NOT_NOTHING_NOTHING, NOTHING_NOTHING_NOT_ASSERT_SET_A, NOTHING_NOTHING_NOT_TOGGLE_TOGGLE};
    private static HashMap parameterLookUp;

    private SelectAction(String str, String str2) {
        super(str, str2);
        if (parameterLookUp == null) {
            parameterLookUp = new HashMap();
        }
        parameterLookUp.put(str, this);
    }

    public static final SelectAction Parse(String str) {
        String trim = str.trim();
        if (parameterLookUp.containsKey(trim)) {
            return (SelectAction) parameterLookUp.get(trim);
        }
        throw new IllegalArgumentException(String.format("'%s' is not recognised as a value of %s", new Object[]{str, SelectAction.class.getName()}));
    }

    public static final SelectAction[] getValues() {
        return PRIVATE_VALUES;
    }
}
