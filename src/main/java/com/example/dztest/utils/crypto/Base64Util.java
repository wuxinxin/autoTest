package com.example.dztest.utils.crypto;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

/**
 * @author
 */
public class Base64Util {
    /**
     *
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    /**
     * @param data
     * @return
     */
    public static String encode(String data) {
        return Base64.encodeBase64String(StringUtils.getBytesUtf8(data));
    }

    /**
     * @param data
     * @return
     */
    public static byte[] decode(String data) {
        return Base64.decodeBase64(data);
    }

    /**
     * @param data
     * @return
     */
    public static String decodeToString(String data) {
        byte[] result = decode(data);
        return StringUtils.newStringUtf8(result);
    }

    /**
     * @param data
     * @return
     */
    public static String encodeUrlSafe(byte[] data) {
        return StringUtils.newStringUtf8(Base64.encodeBase64URLSafe(data));
    }
}
