package com.app.apprfid.util;

public class d {
    public static boolean a(String str) {
        String str2 = "0123456789";
        for (int i = 0; i < str.length(); i++) {
            if (!"0123456789".contains(str.subSequence(i, i + 1))) {
                return false;
            }
        }
        return true;
    }
}
