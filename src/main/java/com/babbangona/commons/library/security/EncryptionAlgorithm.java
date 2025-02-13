package com.babbangona.commons.library.security;

public interface EncryptionAlgorithm {
    String decrypt(String encryptedString, String key);
    String encrypt(String plainString, String key);
    String appVersionKey();
    String appVersionRequiredError();
    String appVersionNotSupportedError();
    String deviceIdKey();
    String deviceIdRequiredError();
    String timeStampKey();
    String timeStampFieldError();
}
