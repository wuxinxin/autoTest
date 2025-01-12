package com.example.dztest.utils.crypto;

import cn.hutool.core.codec.Base64;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/**
 * RSA加解密工具类
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

        String privateKey = getPrivateKey(key);

        String publicKey = getPublicKey(key);

        System.out.println("privateKey = " + privateKey);

        System.out.println("publicKey = " + publicKey);

        String timestamp = "1582011346394";

        System.out.println("timestamp = " + timestamp);

        //全平台质检appid：5f06a082d7814ae8b8cd2d8076da4bc5
        String appId = "5f06a082d7814ae8b8cd2d8076da4bc5";
        privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCRzzUrnaaUZopSvQ7Hkel+M2p0RqOgXq3yGBaEtZMIvbAvAT1kzRDZ3zoM5yN52FfrRM9qnAGU5xVt9AiZgTsSQ8Bb3imk0ovdWAvhtdq3MoPn4fJUGhqExc7CP22ZCMTsgLFWWzDvPuO16YKmgGqOBW3MgEo2xBme2uNr0jDSDUPk52DYIrte8qfM7UB50Yagc/CsAgqlMA72jmtEK4ENHYeqzqchUG0cTPOrtB/M+cTjb45oUSW2ih02BjUVEqwBWS6Y3Wx5NF4rkTfffUzNASb11N2bKfbMoS7ycqF9c6ZQwbcNduYaD7ABInDK3psi+0PgLuZwMnm2RXLfRuEVAgMBAAECggEAWNzEldlVvVmk8XnhOQWXHN+vAEBQ1pr9Pe8qQiT7ADfC2VLTHMzReUbPdS+k8kxEgNEF6/46BIq1B7bTSuJ0otQBtrt1+8qJjRNs4llXe5DgzQBqFJF4GfUH5mCLpdmvUke4TF31O8nDM4mlo13eEsICQcDp+eiXiLX0JjEKqeiWD21gnrvsYuFMn5QB6YBluo/J35riBpoTLuJroOISuO/ls8Ls5DZ4C9BW8CfQyFcblnRwV9Jrm3pR0JlpcTZ2ACvAS7V1uVaac3ZMuDD2wLg+i42ZgwRwhGfuW85gfzTMW/NRSjpCLTyQDng5YV8U5lOg23dtuX0abV9/77nOIQKBgQDpIisKZBE1gCcdyE3kHHBJgSfIb23ga6FU37oRwGJr6ed4aQLFL87LABEzTkElb7x2IhzBmPhF7uUWt91j1KbrgQ1Kv7QqiIRrjU1Q6rhuoFt+TMamMqaY1wKonzpg8WErYcTyjSywFOr8eOypeIHBgrYRrGbQyi85K4ngsZOz2QKBgQCgHGNpotxCdPh4+4YAeb8ATZ8oJPYkaInGFoL1icYZKaElCDkBeh173qLWFWBb4hA0a+kvtMZr+Xjhrw/nUL1wZ5IDS+lDR/Mthu+5xC5UUme8QwQfAon/VM2cLTT7O0kZE1tP2He3LiPQnjL4430vHoBInzcLNuSie1MotaIdnQKBgQCeoiNLVL/o+aSkPVHS8A7v1TRuzHOli1ch24JO5euSpnxckIfUCS3bL9aEZLErehZxA9ExYpc9bsrvZHj+nwpdoVicDknXz0DbtjBv6OdKT08yZ6ecG0cFJPioKICeymBmFobezHUl83XaCaZvwpH4TKr656amV40+h4OwIuTwMQKBgGBTM+vlsyW4QZRnniLDHAoOqOX59qvIyLqH6JnYHXod9XjWDZW55esT8lth2OrugllHYq70zzs+h18fdy3vAUmSxeF91gRIUSDewXK9eDH6sbYti1ksqeEXH+wJtoo/DKctYg4TaVKnS5zC0F+0XSfv7bXDM9ZnW/30u/8N505xAoGBANvLXXsGg1YPIbWYQpxmGL90H1650DXwzMZsOWsSQlmcu1glHhG6vXVpeJkjd1pWFcYxL85sWurZMG+l3EO3Xs1LSDMtwIKeWw7VS6sF4LwNia4jZzXbKHmB1egwwku+nDynKg1iB/wtnu29sOlWFhYHXSjQn7rhmYrRv2YrXCD1";

        System.out.println("appId = " + appId);

        String sign = sign((appId + timestamp).getBytes(),privateKey);

        System.out.println("sign = " + sign);
        publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkc81K52mlGaKUr0Ox5HpfjNqdEajoF6t8hgWhLWTCL2wLwE9ZM0Q2d86DOcjedhX60TPapwBlOcVbfQImYE7EkPAW94ppNKL3VgL4bXatzKD5+HyVBoahMXOwj9tmQjE7ICxVlsw7z7jtemCpoBqjgVtzIBKNsQZntrja9Iw0g1D5Odg2CK7XvKnzO1AedGGoHPwrAIKpTAO9o5rRCuBDR2Hqs6nIVBtHEzzq7QfzPnE42+OaFEltoodNgY1FRKsAVkumN1seTReK5E3331MzQEm9dTdmyn2zKEu8nKhfXOmUMG3DXbmGg+wASJwyt6bIvtD4C7mcDJ5tkVy30bhFQIDAQAB";

        System.out.println("鉴权结果：" + verify((appId + timestamp).getBytes(),publicKey,sign));
    }
}