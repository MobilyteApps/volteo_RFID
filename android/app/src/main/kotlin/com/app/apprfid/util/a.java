package com.app.apprfid.util;

import java.nio.charset.Charset;

public class a {
    private static final char[] a = "0123456789abcdef".toCharArray();

    public static String a(String str) {
        byte[] bytes = str.getBytes(Charset.forName("US-ASCII"));
        char[] cArr = new char[(bytes.length * 2)];
        for (int i = 0; i < bytes.length; i++) {
            cArr[i * 2] = a[(bytes[i] & 240) >>> 4];
            cArr[(i * 2) + 1] = a[bytes[i] & 15];
        }
        return new String(cArr);
    }

    public static String b(String str) {
        if (str == null || str.length() < 2) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i += 2) {
            int parseInt = Integer.parseInt(str.substring(i, i + 2), 16);
            if (parseInt < 32 || parseInt >= 127) {
                return null;
            }
            sb.append((char) parseInt);
        }
        return sb.toString();
    }
}
