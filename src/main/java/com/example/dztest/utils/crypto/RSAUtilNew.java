package com.example.dztest.utils.crypto;


import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.example.dztest.utils.auth.PasswordAESTools;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class RSAUtilNew {
    /**
     * 密钥长度(bit)
     */
    public static final int KEY_LENGTH = 1024;// 最好用2048

    public static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * <p>
     * 单次解密最大密文长度，这里仅仅指1024bit 长度密钥
     * </p>
     *
     * @see #MAX_ENCRYPT_BLOCK
     */
    public static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 加密算法
     */
    public static final String ALGORITHM_RSA = "RSA";

    /**
     * 算法/模式/填充
     */
    public static final String CIPHER_TRANSFORMATION_RSA = "RSA/ECB/PKCS1Padding";

    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
     * UTF-8字符集
     **/
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * GBK字符集
     **/
    public static final String CHARSET_GBK = "GBK";

    public static final String CHARSET = CHARSET_UTF8;

    /**
     * 得到公钥
     *
     * @param key     密钥字符串（经过base64编码）
     * @param charset
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key, String charset)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key.getBytes(charset));

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 得到私钥
     *
     * @param key     密钥字符串（经过base64编码）
     * @param charset
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key, String charset)
            throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decodeBase64(key.getBytes(charset));

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 得到密钥字符串（经过base64编码）
     *
     * @return
     */
    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = new String(Base64.encodeBase64(keyBytes), CHARSET);
        return s;
    }

    /**
     * 公钥加密
     *
     * @param content   待加密内容
     * @param publicKey 公钥
     * @param charset   字符集，如UTF-8, GBK, GB2312
     * @return 密文内容
     * @throws Exception
     */
    public static String rsaEncrypt(String content, String publicKey,
                                    String charset) throws Exception {
        try {
            PublicKey pubKey = getPublicKey(publicKey, charset);
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] data = StringUtils.isEmpty(charset) ? content.getBytes()
                    : content.getBytes(charset);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = Base64.encodeBase64(out.toByteArray());
            out.close();

            return StringUtils.isEmpty(charset) ? new String(encryptedData)
                    : new String(encryptedData, charset);
        } catch (Exception e) {
            throw new Exception(
                    "error occured in rsaEncrypt: EncryptContent = " + content
                            + ",charset = " + charset, e);
        }
    }

    /**
     * 公钥加密
     *
     * @param content   待加密内容
     * @param publicKey 公钥
     * @return 密文内容
     * @throws Exception
     */
    public static String rsaEncrypt(String content, String publicKey)
            throws Exception {
        return rsaEncrypt(content, publicKey, "utf8");
    }

    /**
     * 私钥解密
     *
     * @param content    待解密内容
     * @param privateKey 私钥
     * @param charset    字符集，如UTF-8, GBK, GB2312
     * @return 明文内容
     * @throws Exception
     */
    public static String rsaDecrypt(String content, String privateKey,
                                    String charset) throws Exception {
        try {
            PrivateKey priKey = getPrivateKey(privateKey, charset);
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] encryptedData = StringUtils.isEmpty(charset) ? Base64
                    .decodeBase64(content.getBytes()) : Base64
                    .decodeBase64(content.getBytes(charset));
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet,
                            MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen
                            - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();

            return StringUtils.isEmpty(charset) ? new String(decryptedData)
                    : new String(decryptedData, charset);
        } catch (Exception e) {
            throw new Exception("error occured in rsaDecrypt: EncodeContent = "
                    + content + ",charset = " + charset, e);
        }
    }

    /**
     * 私钥解密
     *
     * @param content    待解密内容
     * @param privateKey 私钥
     * @return 明文内容
     * @throws Exception
     */
    public static String rsaDecrypt(String content, String privateKey)
            throws Exception {
        return rsaDecrypt(content, privateKey, "utf8");
    }

    /**
     * 获得密钥对
     *
     * @return
     * @throws NoSuchAlgorithmException KeyPair
     * @Title creatKeyPair
     */
    public static KeyPair creatKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 密钥位数
        keyPairGen.initialize(KEY_LENGTH);
        // 密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();
        return keyPair;
    }

    public static String getEncodePwd(String data) {
        //加密顺序：1、先对称加密    2、时间戳+对称加密的密码  进行rsa加密   3、base64加密
        String base64Pwd = "";
        try {

            //1.对称加密
            String encrypt = PasswordAESTools.encrypt(data);

            //2.时间戳+对称加密的密码进行rsa加密
            String time2encrypt = System.currentTimeMillis() + encrypt;
            String time2encryptRsa = RSAUtilNew.rsaEncrypt(time2encrypt,
                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCpIcruHmD2euTgXifeEmpeItIH9E/Qgorpttq9Zr" +
                            "6YfAYEfnfor4z6FFaTTx2Co/ncF4Va1xXc95OTifOvrMWTBYDHBZO1njItqVJSrKmHIEX9oDzuzDheN+" +
                            "7tbv5bfaA9rcI/RZAtB3Jck3MsV4dMjmNfwe+TmMYHulBN7IN+bwIDAQAB");

            //3.base64加密
            base64Pwd = new String(Base64.encodeBase64(time2encryptRsa.getBytes()));


            return base64Pwd;

        } catch (Exception e) {
            System.out.println("加密异常");
        }
        return "ERROR";
    }

    public static void main(String[] args) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email","wanglihua6046@163.com");
            jsonObject.put("phone","18500318710");
            jsonObject.put("empno","18500318710");
            jsonObject.put("thirdpartAccount","wanglihua6046@163.com");
            jsonObject.put("timestamp","1660706862042");

            // 页面上的token
            //String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC24PJaSaqxe4K/DIUz6GKrxB2Wfm9AdXDLxEM8tIiVKMEB0PLpN+W0RJfUHPKNCTnpPPmOi0psPSsYa1slJet//QLjPyrZGyqnNG8JJ4Yxt+4iGfYHzBeq1BpxIWDqloKn29jt+VqHb9B1vBhPlhDH/XFauLf+1WGPv/G4wD4+DwIDAQAB";
            //String result = rsaEncrypt(jsonObject.toString(), publicKey);
            //System.out.println(result);


            // keyPair = creatKeyPair();
            //String privateKey = getKeyString(keyPair.getPrivate());
            //String publicKey = getKeyString(keyPair.getPublic());

            String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCpIcruHmD2euTgXifeEmpeItIH9E/Qgorpttq9Zr6YfAYEfnfor4z6FFaTTx2Co/ncF4Va1xXc95OTifOvrMWTBYDHBZO1njItqVJSrKmHIEX9oDzuzDheN+7tbv5bfaA9rcI/RZAtB3Jck3MsV4dMjmNfwe+TmMYHulBN7IN+bwIDAQAB";
            String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKkhyu4eYPZ65OBeJ94Sal4i0gf0T9CCium22r1mvph8BgR+d+ivjPoUVpNPHYKj+dwXhVrXFdz3k5OJ86+sxZMFgMcFk7WeMi2pUlKsqYcgRf2gPO7MOF437u1u/lt9oD2twj9FkC0HclyTcyxXh0yOY1/B75OYxge6UE3sg35vAgMBAAECgYB5zJgzezUgy8ffvG7BgkmPmUvf5pVD937KaKCGHWPMtCQxQFJiA2pm/haK50K9PhFF6rpifNqF0tU4PvR64tU0q1VgjBXNrWxmzgVW7B6AJB1aVHR6A/H3RdCdFkeziJOai7iAkjbG7sZy6cILUvPSiYjmGoaFdctTb7MdVXXxwQJBAN0WMMrRZhdgG4AgvBvlI8/ju5Wvw9rGF4eSQohrn0UknaPUWXldYMA4r12GtA6tzU/X41EYhFKnSbqAlQikutkCQQDD1z6s2XklLIYCKRPxcK4ybyd/DDtzvEm6RywLtrhmhzG8dJFcYHwv5M+6ozc7d6L4tMFaUMG/6+DZeyoUNuaHAkAZGTvQypTccnysvwqb4BeAsknJZHrHi1WAfoovEoiyQMXko2bE8GTbbZP6+h/WysRUHB4iRrY+697i3VYN9SWBAkAC0uq7cVNW3uJ7y33XIGnSnlmyckm1LITfo8EV1ieoitCDyCaEb3u68RCxv0K/n8UA0xJqq/lvPAZ8/FKqAfSnAkEAzK1Jw/UUsccXu/jOMa/3xitGGr/Tz4s4eie46Fjhpw8SCEew3cWNJ3k/la99dMbR2F+/Yq8BZfHRFfEsYXgrbQ==";
            System.out.println("publicKey=" + publicKey);
            System.out.println("privateKey=" + privateKey);

            String result = rsaEncrypt("Qwe12345!", publicKey);
            System.out.println(result);

            String originalPwd = rsaDecrypt(result, privateKey);
            System.out.println(originalPwd);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}