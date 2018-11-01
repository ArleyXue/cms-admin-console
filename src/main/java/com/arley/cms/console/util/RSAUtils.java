
package com.arley.cms.console.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtils {
    private static final String ALGORITHM = "RSA";
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static final String CHARSET = "utf-8";
    private static final int KEY_SIZE = 1024;

    private RSAUtils() {
    }

    public static KeyPair generateKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    public static String getPublicKey(KeyPair keyPair) {
        return new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
    }

    public static String getPrivateKey(KeyPair keyPair) {
        return new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
    }

    public static String encryptByPrivateKey(String data, String key) throws Exception {
        byte[] results = encryptByPrivateKey(data.getBytes(CHARSET), key.getBytes());
        return new String(Base64.encodeBase64(results));
    }

    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        if (data.length > 117) {
            throw new IllegalBlockSizeException("Rsa encryptByPrivateKey Exception:Data must not be longer than 117 bytes.Data length:" + data.length);
        } else {
            byte[] keyBytes = Base64.decodeBase64(key);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(1, privateKey);
            return cipher.doFinal(data);
        }
    }

    public static String encryptByPublicKey(String data, String key) throws Exception {
        byte[] encrypt = encryptByPublicKey(data.getBytes(CHARSET), key.getBytes());
        return new String(Base64.encodeBase64(encrypt));
    }

    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {
        if (data.length > 117) {
            throw new IllegalBlockSizeException("Data must not be longer than 117 bytes.Data length:" + data.length);
        } else {
            byte[] keyBytes = Base64.decodeBase64(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(1, publicKey);
            return cipher.doFinal(data);
        }
    }

    public static String decryptByPrivateKey(String data, String key) throws Exception {
        byte[] dataBytes = Base64.decodeBase64(data.getBytes());
        byte[] resultBytes = decryptByPrivateKey(dataBytes, key.getBytes());
        return new String(resultBytes, CHARSET);
    }

    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateKey);
        return cipher.doFinal(data);
    }

    public static String decryptByPublicKey(String data, String key) throws Exception {
        byte[] decode = Base64.decodeBase64(data.getBytes());
        byte[] byPublicKey = decryptByPublicKey(decode, key.getBytes(CHARSET));
        return new String(byPublicKey, CHARSET);
    }

    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, publicKey);
        return cipher.doFinal(data);
    }

    public static String sign(String content, String privateKey, String charset) throws Exception {
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(priPKCS8);
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(priKey);
        signature.update(content.getBytes(charset));
        byte[] signed = signature.sign();
        return new String(Base64.encodeBase64(signed));
    }

    public static boolean doCheck(String content, String sign, String publicKey, String encoding) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        byte[] encodedKey = Base64.decodeBase64(publicKey);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initVerify(pubKey);
        signature.update(content.getBytes(encoding));
        boolean verified = signature.verify(Base64.decodeBase64(sign));
        return verified;
    }

    public static boolean doCheck(String content, String sign, String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        byte[] encodedKey = Base64.decodeBase64(publicKey);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initVerify(pubKey);
        signature.update(content.getBytes());
        boolean verified = signature.verify(Base64.decodeBase64(sign));
        return verified;
    }
}
