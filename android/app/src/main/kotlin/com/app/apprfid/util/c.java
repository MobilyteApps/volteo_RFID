package com.app.apprfid.util;

public class c {
    public static String a(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 7 || (str.length() >= 11 && str.length() <= 13)) {
            return c(str);
        }
        return null;
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 7 || str.length() == 13) {
            return c(str);
        }
        return null;
    }

    public static String c(String str) {
        int i = 0;
        String str2 = "0000000000000";
        if (str == null || str.length() > 13) {
            return null;
        }
        try {
            String str3 = "0000000000000".substring(0, 13 - str.length()) + str;
            int i2 = 0;
            for (int i3 = 0; i3 < str3.length(); i3 += 2) {
                i2 += Integer.parseInt(str3.substring(i3, i3 + 1));
            }
            for (int i4 = 1; i4 < str3.length(); i4 += 2) {
                i += Integer.parseInt(str3.substring(i4, i4 + 1));
            }
            int i5 = ((i2 * 3) + i) % 10;
            return i5 == 0 ? "0" : Integer.toString(10 - i5);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
