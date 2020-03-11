package com.app.apprfid.util;

public class g {
    public static boolean a(String str) {
        if (str == null || str.length() % 4 != 0) {
            return false;
        }
        try {
            String str2 = new String(str);
            for (int i = 0; i < str.length() / 4; i++) {
                String substring = str2.substring(0, 4);
                str2 = str2.substring(4);
                Integer.parseInt(substring, 16);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean b(String str) {
        String str2 = "0123456789ABCDEFabcdef";
        for (int i = 0; i < str.length(); i++) {
            if (!"0123456789ABCDEFabcdef".contains(str.subSequence(i, i + 1))) {
                return false;
            }
        }
        return true;
    }
}
