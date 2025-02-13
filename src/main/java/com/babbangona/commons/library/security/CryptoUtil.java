package com.babbangona.commons.library.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class CryptoUtil {
    public static final String AES_KEY = "B374A26A71490437AA024E4FADD5B497FDFF1A8EA6FF12F6FB65AF2720B59CCF";
    private static final String ALGORITHM = "AES/CTR/NoPadding";
    private static final int BITS128 = 16;
    private static final int BITS256 = 32;

    public static String encrypt128(String key, String message) throws Exception {
        return Base64.getEncoder().encodeToString(encrypt(key, message, 16));
    }

    public static String decrypt128(String key, String message) throws Exception {
        byte[] msg = Base64.getDecoder().decode(message);
        return new String(decrypt(key, msg, 16), StandardCharsets.UTF_8);
    }

    public static String encrypt256(String key, String message) throws Exception {
        return Base64.getEncoder().encodeToString(encrypt(key, message, 32));
    }

    public static String encrypt256(String message) throws Exception {
        return Base64.getEncoder().encodeToString(encrypt(AES_KEY, message, 32));
    }

    public static String decrypt256(String key, String message) throws Exception {
        byte[] msg = Base64.getDecoder().decode(message);
        return new String(decrypt(key, msg, 32), StandardCharsets.UTF_8);
    }

    public static String decrypt256(String message) throws Exception {
        byte[] msg = Base64.getDecoder().decode(message);
        return new String(decrypt(AES_KEY, msg, 32), StandardCharsets.UTF_8);
    }

    private static byte[] encrypt(String keystring, String message, int bits) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] nonceBytes = new byte[8];
        random.nextBytes(nonceBytes);
        IvParameterSpec nonce = new IvParameterSpec(Arrays.copyOf(nonceBytes, 16));
        Key key = generateKey(keystring, bits);
        Cipher c = Cipher.getInstance("AES/CTR/NoPadding");
        c.init(1, key, nonce);
        byte[] ciphertextWithoutNonce = c.doFinal(message.getBytes("UTF-8"));
        byte[] encValue = Arrays.copyOf(nonceBytes, nonceBytes.length + ciphertextWithoutNonce.length);
        System.arraycopy(ciphertextWithoutNonce, 0, encValue, 8, ciphertextWithoutNonce.length);
        return encValue;
    }

    private static byte[] decrypt(String keystring, byte[] message, int bits) throws Exception {
        byte[] nonceBytes = Arrays.copyOf(Arrays.copyOf(message, 8), 16);
        IvParameterSpec nonce = new IvParameterSpec(nonceBytes);
        Key key = generateKey(keystring, bits);
        Cipher c = Cipher.getInstance("AES/CTR/NoPadding");
        c.init(2, key, nonce);
        return c.doFinal(message, 8, message.length - 8);
    }

    private static Key generateKey(String keystring, int bits) throws Exception {
        byte[] keyBytes = new byte[bits];
        byte[] key = new byte[bits];

        for(int i = 0; i < bits; ++i) {
            keyBytes[i] = (byte)keystring.codePointAt(i);
        }

        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(1, secretKey);
        key = cipher.doFinal(keyBytes);

        for(int i = 0; i < bits - 16; ++i) {
            key[16 + i] = key[i];
        }

        return new SecretKeySpec(key, "AES");
    }

    private CryptoUtil() {
    }
}

