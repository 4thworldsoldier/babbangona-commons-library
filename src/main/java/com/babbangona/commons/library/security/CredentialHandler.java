package com.babbangona.commons.library.security;

import com.babbangona.commons.library.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CredentialHandler {

    private final KeyCryptoService keyCryptoService;

    public String generateEncodedCredential(String credential) throws CustomException {
        String salt = keyCryptoService.getSalt();
        String encodedCredential;
        try {
            String hash = keyCryptoService.hash(credential, salt);
            encodedCredential = hash + "#" + salt;
        } catch (KeyCryptoService.CryptoException e) {
            log.error("ERROR GENERATING ENCODED CREDENTIAL", e);
            throw new CustomException("Error generating encoded credential");
        }
        return encodedCredential;
    }

    public void validateCredential(String encodedCredential, String credential) throws CustomException {
        String[] parts = encodedCredential.split("#");
        if (parts.length != 2) {
            throw new CustomException("Invalid encoded credential");
        }
        String hash = parts[0];
        String salt = parts[1];
        try {
            if (!keyCryptoService.compare(hash, credential, salt)) {
                throw new CustomException("Invalid encoded credential");
            }
        } catch (KeyCryptoService.CryptoException e) {
            throw new CustomException("Error validating credential");
        }
    }
}
