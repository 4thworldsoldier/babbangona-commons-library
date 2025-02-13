package com.babbangona.commons.library.utils;

import com.babbangona.commons.library.exceptions.CustomException;
import com.babbangona.commons.library.logging.CommonsLogger;
import com.babbangona.commons.library.logging.CommonsLoggerFactory;
import com.babbangona.commons.library.security.CryptoUtil;
import com.babbangona.commons.library.security.KeyCryptoService;
import com.babbangona.commons.library.cache.RedisClientService;

import java.util.function.BinaryOperator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationStringHandler {

    private static final CommonsLogger log = CommonsLoggerFactory.getLogger(AuthorizationStringHandler.class);
    private static final BinaryOperator<String> GET_KEY = (s1, s2) -> s1 + "#" + s2;

    private final RedisClientService redisClientService;
    private final KeyCryptoService cryptoService;
    private final BaseConfigProperties baseConfigProperties;

    public void validateAuthorizationString(String authorizationString, String authorizationKeyPart) throws CustomException {
        String decryptedAuthorizationCode;
        try {
            decryptedAuthorizationCode = CryptoUtil.decrypt256(authorizationString);
        } catch (Exception e) {
            log.error("ERROR VALIDATING AUTHORIZATION STRING", e);
            throw new CustomException(ResponseConstants.AUTHORIZATION_FAILURE);
        }
        String authorizationCodeKey = GET_KEY.apply(authorizationKeyPart, authorizationString);
        String authorizationCode = redisClientService.getValue(authorizationCodeKey);

        if (StringUtils.isBlank(authorizationCode) || !authorizationCode.equalsIgnoreCase(decryptedAuthorizationCode)) {
            throw new CustomException(ResponseConstants.AUTHORIZATION_FAILURE);
        }
        redisClientService.deleteKey(authorizationCodeKey);
    }

    public String generateAuthorizationString(String authorizationKeyPart) throws Exception {
        String authorizationCode = cryptoService.generateActivationCode(baseConfigProperties.getOtpLength());
        String authorizationString = CryptoUtil.encrypt256(authorizationCode);
        String key = GET_KEY.apply(authorizationKeyPart, authorizationString);
        Long authorizationCodeExpiryTimeout = baseConfigProperties.getAuthorizationCodeExpiryTimeout();
        redisClientService.setKeyValueWithLongTimeout(key, authorizationCode, authorizationCodeExpiryTimeout);
        return authorizationString;
    }
}
