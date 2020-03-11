// 
// Decompiled by Procyon v0.5.36
// 

package com.app.apprfid.asciiprotocol.utils;

import android.util.Log;

public class StringHelper
{
    public static boolean isNullOrEmpty(final String string) {
        return string == null || string.equals("");
    }
    
    public static int comparableVersionValue(final String version) {
        if (version == null) {
            return -1;
        }
        final String[] split = version.split("\\.");
        int n = 65536;
        int n2 = 0;
        for (final String s : split) {
            try {
                n2 += Integer.parseInt(s) * n;
                n >>= 8;
            }
            catch (Exception ex) {
                Log.d("", ex.getMessage());
                ex.printStackTrace();
                n2 = -1;
                break;
            }
        }
        return n2;
    }
    
    public static Integer parseInteger(final String value) {
        if (value == null) {
            return null;
        }
        Integer value2 = null;
        try {
            value2 = Integer.parseInt(value);
        }
        catch (Exception ex) {}
        return value2;
    }
}
