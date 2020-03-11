package com.app.apprfid.util;

import java.math.BigInteger;

public class b {
    public static int a(String str) {
        return new BigInteger(str, 2).intValue();
    }

    public static String a(int i, int i2, String str) {
        String bigInteger = new BigInteger(str, 2).toString(i2);
        if (bigInteger.length() > i) {
            return null;
        }
        if (bigInteger.length() == i) {
            return bigInteger;
        }
        return String.format("%" + i + "s", new Object[]{bigInteger}).replace(' ', '0');
    }

    public static String a(int i, String str) {
        return a(i, 10, str);
    }

    public static String b(int i, int i2, String str) {
        String bigInteger = new BigInteger(str, i2).toString(2);
        if (bigInteger.length() > i) {
            return null;
        }
        if (bigInteger.length() == i) {
            return bigInteger;
        }
        return String.format("%" + i + "s", new Object[]{bigInteger}).replace(' ', '0');
    }

    public static String b(int i, String str) {
        return b(i, 10, str);
    }

    public static String c(int i, String str) {
        return a(i, 16, str);
    }

    public static String d(int i, String str) {
        return b(i, 16, str);
    }
}
