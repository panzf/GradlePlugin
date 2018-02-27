package com.zykj.plugin.helper

import java.security.MessageDigest;

public class Utils {
    static HEX_DIGITS = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f']

    public static String md5Hex(byte[] b) {
        if (b != null) {
            def sb = new StringBuilder()
            for (int i = 0; i < b.length; i++) {
                sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4])
                sb.append(HEX_DIGITS[(b[i] & 0x0f)])
            }
            return sb.toString()
        }
        return null
    }

    public static String md5Hex(String src) {
        if (src != null) {
            try {
                def messageDigest = MessageDigest.getInstance("MD5")
                messageDigest.update(src.getBytes())
                return md5Hex(messageDigest.digest())
            } catch (Exception e) {
            }
        }
        return null
    }
}