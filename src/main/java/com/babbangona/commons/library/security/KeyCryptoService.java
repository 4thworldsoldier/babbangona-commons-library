package com.babbangona.commons.library.security;

import java.io.Serial;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;

@Component
public class KeyCryptoService implements Serializable {

    private static final String ALGORITHM = "AES/CTR/NoPadding";
    static final int BITS128 = 16;
    static final int BITS256 = 32;
    private static final SecureRandom random = new SecureRandom();
    @Serial
    private static final long serialVersionUID = -3238426794139182008L;

    public String generateActivationCode(int otpLength) {
        return RandomStringUtils.randomNumeric(otpLength);
    }

    public String hash(String source, String salt) throws CryptoException {
        return new Sha256Hash(source, salt).toString();
    }

    public boolean compare(String source, String target, String salt) throws CryptoException {
        source = hash(source, salt);
        return source.equals(target);
    }

    public String encrypt(String source, String key) throws CryptoException {
        try {
            SecretKey keySpec = new SecretKeySpec(Hex.decode(key), "DESede");
            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] b = Hex.decode(source);
            byte[] tt = cipher.doFinal(b);
            return Hex.toHexString(tt);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new CryptoException("Error encrypting data", e);
        }
    }

    public String decrypt(String source, String key) throws CryptoException {
        try {
            SecretKey keySpec = new SecretKeySpec(Hex.decode(key), "DESede");
            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] b = Hex.decode(source);
            byte[] tt = cipher.doFinal(b);
            return Hex.toHexString(tt);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new CryptoException("Error encrypting data", e);
        }
    }

    public String getSalt() {
        byte[] salt = new byte[20];
        random.nextBytes(salt);
        return Hex.toHexString(salt);
    }

    public static class CryptoException extends Exception {
        public CryptoException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
