// 
// Decompiled by Procyon v0.5.36
// 

package com.app.apprfid.asciiprotocol.utils;

public class HexEncoding
{
    public static String bytesToString(final byte[] bytes) {
        final StringBuilder sb = new StringBuilder();
        for (int length = bytes.length, i = 0; i < length; ++i) {
            sb.append(String.format("%02x", bytes[i] & 0xFF));
        }
        return sb.toString();
    }
    
    public static byte[] stringToBytes(final String s) {
        final int length = s.length();
        final byte[] array = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            array[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return array;
    }
}
