package com.example.dztest.utils.crypto;

import com.example.dztest.utils.crypto.AESCrypto.Algorithm;
import com.example.dztest.utils.crypto.AESCrypto.Mode;
import com.example.dztest.utils.crypto.AESCrypto.Padding;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

/**
 */
public class AESUtil {
    /**
     * @return
     */
    public static byte[] generateKey(AESCrypto.Algorithm algorithm) {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(algorithm.getName());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGenerator.init(algorithm.getKeySize());
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    /**
     *
     * @param key
     * @return
     */
    private static Key toKey(AESCrypto.Algorithm algorithm, byte[] key) {
        try {
            KeySpec keySpec = null;
            if (algorithm.getName().equals("DES")) {
                keySpec = new DESKeySpec(key);
            } else if (algorithm.getName().equals("DESede")) {
                keySpec = new DESedeKeySpec(key);
            } else if (algorithm.getName().equals("AES")) {
                return new SecretKeySpec(key, "AES");
            } else {
                return new SecretKeySpec(key, algorithm.getName());
            }

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm.getName());
            return keyFactory.generateSecret(keySpec);
        } catch (NoSuchAlgorithmException e) {
            return new SecretKeySpec(key, algorithm.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param data
     * @param mode
     * @param padding
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] data, AESCrypto.Algorithm algorithm,
                                 AESCrypto.Mode mode, AESCrypto.Padding padding, byte[] key) {
        Key k = toKey(algorithm, key);

        String algorithmKey = algorithm.getName() + "/" + mode.getName() + "/" + padding.getName();

        try {
            Cipher cipher = Cipher.getInstance(algorithmKey);

            cipher.init(Cipher.ENCRYPT_MODE, k);

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param data
     * @param mode
     * @param padding
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] data, AESCrypto.Algorithm algorithm,
                                 AESCrypto.Mode mode, AESCrypto.Padding padding, byte[] key) {
        Key k = toKey(algorithm, key);

        String algorithmKey = algorithm.getName() + "/" + mode.getName() + "/" + padding.getName();

        try {
            Cipher cipher = Cipher.getInstance(algorithmKey);

            cipher.init(Cipher.DECRYPT_MODE, k);

            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param data
     * @return
     */
    public static String toHex(byte[] data) {
        return Hex.encodeHexString(data);
    }

    /**
     *
     * @param hex
     * @return
     */
    public static byte[] parseHex(String hex) {
        try {
            return Hex.decodeHex(hex.toCharArray());
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        byte[] key = AESUtil.generateKey(Algorithm.AES128);
        key = "aU2bt/lZoGhwdIS1iYZz9g==".getBytes();
        String aesKey = Base64Util.encode(key);
        aesKey = "zHNLh8R50KvcBxMmLA0HZQ==";
        System.out.println("密钥：" + aesKey);

//        System.out.println("======入参加密demo============");
//        Map<String,String> request = new HashMap<String,String>();
//        request.put("name", "马上消费金融");
//        request.put("address", "成都高新天府二街");
        String str = "vYq3jVGIKkopvfjq3KwZhdp88lu85mlAhUL0UpQXfs61EWcqqpyYW+MmoaebJVKlbqL0d6j17gzfyD1IRnAAsRXzeFor16+Jx3pQW5Pu0ihe4ozN0tce07UauRhJ0m34NJvl+AJgrxrYE9IOeMrkEsAQs5weTVO5V4CU2A/brhpuOGeKwHAB2Txnwf/cni9gxsExMiFo12ytu1c9bqlgfettI6OOdGsTRNH6QNBwUFwm0EIdUw39ryvODlIFFhBBs3BG/MX4drYGcwF+2YRCEfPbaIwqhMHesSDkAmCp3wm/XEE+C8xJnrSxfh0XZ4DyL7RToG6BIF5Gk9KWfjVoL9cypGp/9GT2tMT3JMueIdGoutfOQGFP+ynyIUON4cuz1ptbbMdeCSefdsKUf0DXYk+WmccLcRm76oa+ZLmlOWoJJ7WolmHnTBz9WrSCtFTBRWQUEQu4gUrAiZxcJClybBF5NY9DHC4DMUXLPBHPnBQ0zIT0uFux09uun7M4LCI9V+g2x8IZ6FZ35l+QdHM6PWJOvuJ+HeNEHCDMLNJuOz+n8SEXJp6FMbmgyTj1+7FMKDncZscMloH678lxcGB+WEIl6hTx/9MQPJ2Nbf7099RwsyDLeSbOGtHTWxmz67nR5yTQm/oMLDoD+yYhI4SHIFpWgMuHgtreBgBAFF/R5iwxEhQ5+ctxFbqokHJCRrQZSi9hy6ueB/+K705gTpui2UxTXmpxvBQG1AoIN6FgNw/uY+PiUSskyhSq4uHXuo4un9eglIFvDjz5jWMqZbYaka+I9aKdDvcp5mwDXwT5UN7xo6Fi6uSNkBijkXOAebQn";

//        byte[] encryptData = CryptoUtil.encrypt(JSONUtil.toJsonStr(request).getBytes(), Algorithm.AES128, Mode.ECB, Padding.PKCS5Padding, Base64Util.decode(aesKey));
//
//        String encodeString = Base64Util.encode(encryptData);
//        System.out.println("加密后的数据为：" + encodeString);

        byte[] decryptData = AESUtil.decrypt(Base64Util.decode(str), Algorithm.AES128, Mode.ECB, Padding.PKCS5Padding, Base64Util.decode(aesKey));
        String decodeString = new String(decryptData);
        System.out.println("解密后的数据为：" + decodeString);

//        System.out.println("======返回结果加密demo============");
//        Map<String,String> response = new HashMap<String,String>();
//        response.put("code", "10000");
//        response.put("message", "请求成功");
//        response.put("data", JSONUtil.toJsonStr(request));

//        byte[] responseAllEncryptData = CryptoUtil.encrypt(JSONUtil.toJsonStr(response).getBytes(), Algorithm.AES128, Mode.ECB, Padding.PKCS5Padding, Base64Util.decode(aesKey));
//
//        String responseAllEncodeString = Base64Util.encode(responseAllEncryptData);
//        System.out.println("全部结果加密后的数据为：" + responseAllEncodeString);
//
//        byte[] responseEncryptData = CryptoUtil.encrypt(JSONUtil.toJsonStr(request).getBytes(), Algorithm.AES128, Mode.ECB, Padding.PKCS5Padding, Base64Util.decode(aesKey));
//
//        String responseEncodeString = Base64Util.encode(responseEncryptData);
//        System.out.println("对data加密后的数据为：" + responseEncodeString);
    }
}