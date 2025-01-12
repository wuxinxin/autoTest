package com.example.dztest.utils;

import org.springframework.security.crypto.codec.Base64;

import com.alibaba.fastjson.JSON;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;



/**
 * RSA加解密工具类
 * @time 2020-07-23
 */
public class RSAUtil {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair()  {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(2048);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, Object> keyMap = new HashMap<String, Object>(2);
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
            return keyMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
    {
        try {
            Key key = (Key) keyMap.get(PRIVATE_KEY);
            return new String(Base64.encode(key.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
    {
        try {
            Key key = (Key) keyMap.get(PUBLIC_KEY);
            return new String(Base64.encode(key.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data 已加密数据
     * @param privateKey 私钥(BASE64编码)
     *
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey)   {
        try {
            byte[] keyBytes = Base64.decode(privateKey.getBytes());
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateK);
            signature.update(data);
            return new String(Base64.encode(signature.sign()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     *
     * @return
     * @throws Exception
     *
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64.decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decode(sign.getBytes()));
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> key = genKeyPair();

//        String privateKey = getPrivateKey(key);
        String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDJR455NId1nAfP4Do3sBYegoDMq2Jd7oKe2TCSyW5LtZdx7FrChzsxGnteHKhWGzsnFUuYoqm/P0m044UsEG8O1D0Yhv/FpfA+8Vv1BJTpA4EJyhKhdizZUwACH0AQmjTwwWmOvgVYwQ3Hj3/qeEQsToP6FlA+XbaMWXKBI0kWdCDqYUiHy8XBWeSxMqgtp1KlYrfdHhhpQs4JaH7G6ud9hS7spAEGGXsjSgaYXNuiow2ti+JjkHE/8+5y2PIFIdLivsqiAXIn1pRmqkLtD4e90MnJTblenkkCG/1lW19M/StfyLMbjCsZTXHganrnr+JUnXzvk5IEgTFIHKlzJ/F5AgMBAAECggEBAJxLjlyb55vx3zcSPOLFP0fNusozQx0BejhzGn7BH352sfTqvcdUTyhgEfAQfL7wQkylAqCxnvI86LUn/18qG7L+J7sGCVKm+hlGPkvSlPcjm23IlhL+uvC+vMDpF0xNElLpPVh0QZ6bumF+Eqw1W2q0RVNLRGBJUNlHE90nUze077K6vRPSXkcd5VZ8RqjlSbsDr4PBmnanPZlXFnc07bRXU4ajV2dMb1y+eXj2OfaRJXkjOQOJDYvdz1+VWK7WgzlUcztKMa1gy/mGLnFlVKs00gbeibB6fObdpsRqTox08tfsboh5Wr8XfggwiQ+dzn943hX/CxrIixm7HgqMn4kCgYEA9hg1ABiHle8PmqQR18cSFKCKAClvjt/eadjVX+2DUFpreU/WIaaL5KnZd5SQo6IMsTb1sfmY9FoAONl6hONNRNrHrFoMxBL/Y5TnY2IryKsdfgGraNMYr8MGW8Cj7VNj1FDCg0RYCoi6WNdP70Nh2UsrRe+ZKJPyycb84u0I468CgYEA0WGRqvLD63CjWKCsZ0xqMD535hNQsq8+KJsoFVmqDFcj7pZw+ttAuqhVnUtKx8n2B70j78TL3k2sV0+t2IgQNKaWXBEEGTjlgrlDGYkpCRvO3eVkMhsKOYe2IpLfiHo4YvTURbqH0/BF2Cyn9yl8owr/rG00L6kkACrXMnqtv1cCgYBvEg/hwjB3yFUHm6N9+xg6/RYr6oeyOXI93aK4UA+jVZwKPbFMsMmU4+AF66S4ZexKk56ivB9/sZtGDUoR7jEysXfLXz1Bxb1EqbEhlu5h/HEsLP67KgOLWH9nK/QQqkF1UxnnexItdvZ8IHmwaH4iP54BS5mZyeSbk1cR6J/JewKBgAvNQhaupFhq+uoN9GnGJt4dR0jSoePQeOJ1DGdXrHwRlVbnZ+rb3OQohNre2ZI1UgbRpGrpXsxi+GnbdeMUmEU/do4t71s0l8ssghm4X3XB+YEMYv9oAsWHXr7IJI1exjrdrfFYODu7+VPcAIOv9PSDtDGV3jEpiXvw7jxMKer1AoGATNZ1csdRAfeWFzsIRYV7lPOPSMp54ZD9VIUJvDyXX0Ufehv0l/ODO/d/Z/i4v4fc/1WqjXypFty7+Q8VW6AwgA2Llf0AP9eVsUL+urBduk6BbbKk9jVpFXLPpg2/ESUpLv6ZrM9g9BnvsDGhuRf2NCaOH0AdhqNPWsH36pD7mlU=";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyUeOeTSHdZwHz+A6N7AWHoKAzKtiXe6CntkwksluS7WXcexawoc7MRp7XhyoVhs7JxVLmKKpvz9JtOOFLBBvDtQ9GIb/xaXwPvFb9QSU6QOBCcoSoXYs2VMAAh9AEJo08MFpjr4FWMENx49/6nhELE6D+hZQPl22jFlygSNJFnQg6mFIh8vFwVnksTKoLadSpWK33R4YaULOCWh+xurnfYUu7KQBBhl7I0oGmFzboqMNrYviY5BxP/PuctjyBSHS4r7KogFyJ9aUZqpC7Q+HvdDJyU25Xp5JAhv9ZVtfTP0rX8izG4wrGU1x4Gp656/iVJ1875OSBIExSBypcyfxeQIDAQAB";
//        String publicKey = getPublicKey(key);

        System.out.println("privateKey = " + privateKey);

//        System.out.println("publicKey = " + publicKey);

        String timestamp = "1582011346394";

        System.out.println("timestamp = " + timestamp);

        String appId = "f5be6de15d9a4f378edfb955d931b560";

        System.out.println("appId = " + appId);

        String sign = sign((appId + timestamp).getBytes(),privateKey);

        System.out.println("sign = " + sign);

        System.out.println("鉴权结果：" + verify((appId + timestamp).getBytes(),publicKey,sign));
    }
}