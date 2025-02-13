package com.babbangona.commons.library.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class EncryptionAlgorithmImpl implements EncryptionAlgorithm {

    private static final AesUtilAlgorithm aes = new AesUtilAlgorithm(256, 23);

//    public static void main(String[] args) {
//
//        EncrpyptionAlgorithmImpl algorithm = new EncrpyptionAlgorithmImpl();
//
//        String jsonBody = "{\"userName\":\"user\",\"sourceAccount\":\"1234567890\",\"destinationAccount\":\"1098765432\",\"amount\":\"12\"}";
//        String deviceId = "a9d3b920fe3994d53";  // to be passed in header parameter
//        String timestamp =  String.valueOf(System.currentTimeMillis());
//        String encryptionKey = deviceId+timestamp;
//
//        String encryptedTimestamp = algorithm.encrypt(timestamp, deviceId); // to be passed in header parameter
//        String encryptedJsonBody = algorithm.encrypt(jsonBody, encryptionKey);
//
//        //System.out.println("Plain JSON body: " +jsonBody);
//        System.out.println("Plain Device ID: " +deviceId);
//        System.out.println("Plain Timestamp value: " +algorithm.decrypt(encryptedTimestamp, deviceId));
//        System.out.println("Encryption Key: " +encryptionKey);
//
//        System.out.println("============= Encrypted Values ======================");
//        System.out.println("Encrypted Timetamp value: " +encryptedTimestamp);
//        System.out.println("Encrypted JSON body: " +encryptedJsonBody);
//
//        String responseBody = "oFcpzNBlKyBwT3XWGftusQQ719kaAxmnPvHRst4KZXw=";
//        String decryptedResponse = algorithm.decrypt(encryptedJsonBody,encryptionKey);
//        System.out.println("Decrypted body: " + decryptedResponse);
//    }

    public String getDecryptedOptionalTimestamp(String timestamp, String deviceID) {
        return StringUtils.isNotEmpty(timestamp) ? decrypt(timestamp, deviceID) : "";
    }

    @Override
    public String decrypt(String encryptedString, String key) {
        return aes.decrypt("4321", "12345678909876541234567890987654", key, encryptedString);
    }

    @Override
    public String encrypt(String plainString, String key) {
        return aes.encrypt("4321", "12345678909876541234567890987654", key, plainString);
    }

    public String appVersionKey() {
        return "appVersion";
    }

    public String appVersionRequiredError() {
        return "App Version is required";
    }

    public String appVersionNotSupportedError() {
        return "Your App Version is no longer supported";
    }

    public String deviceIdKey() {
        return "deviceID";
    }

    public String deviceIdRequiredError() {
        return "deviceId required";
    }

    @Override
    public String timeStampKey() {
        return "timestamp";
    }

    @Override
    public String timeStampFieldError() {
        return "Timestamp header required";
    }
}
