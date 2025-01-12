package com.example.dztest.utils.auth;


import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author tao.li
 * @date 2019/5/31
 * @desc
 */
public class PasswordAESTools {
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private static final String CHARSET = "utf-8";

    private static final String KEY = "!bcJef01&3@H6D8*";  //16位
    private static final String IV = "0^234A678gab*de@";  //16位

    public static String encrypt(String data) throws Exception {

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);   //参数分别代表 算法名称/加密模式/数据填充方式

            SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(CHARSET), ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes(CHARSET));

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(data.getBytes());

            return Base64Utils.encodeToString(encrypted);

        } catch (Exception e) {
            throw e;
        }
    }

    public static String decrypt(String data) throws Exception {
        try {
            byte[] encrypted = Base64Utils.decodeFromString(data);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes(CHARSET), ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes(CHARSET));

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted);
            return new String(original);
        } catch (Exception e) {
            throw e;
        }
    }

}
