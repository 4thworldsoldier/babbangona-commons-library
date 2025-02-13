package com.babbangona.commons.library.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.BooleanSupplier;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class BaseConfigProperties {
    @Value("${reference.length:12}")
    private Integer referenceLength;

    @Value("${otp.length:6}")
    private Integer otpLength;

    @Value("${otp.expiry.timeout:120}")
    private int otpExpiryTimeout;

    @Value("${otp.validation.count:2}")
    private int otpValidationCount;

    @Value("${otp.validation.timeout:100}")
    private int otpValidationCountTimeout;

    @Value("${authorization.code.expiry.timeout:3600}")
    private Long authorizationCodeExpiryTimeout;

    @Value("${spring.profiles.active:test}")
    private String currentProfile;

    @Value("${account.number.minimum.length:8}")
    private int accountNumberMinimumLength;

    @Value("${account.number.maximum.length:16}")
    private int accountNumberMaximumLength;

    @Value("${bills-payment.collection.account.name:Bills Payment Collection Account}")
    private String billsPaymentCollectionAccountName;

    @Value("${bills-payment.collection.account.number:0023498058}")
    private String billsPaymentCollectionAccountNumber;

    @Value("${card.number.length:16}")
    private int cardNumberLength;

    @Value("${password.length:8}")
    private int passwordLength;

    @Value("${pin.length:6}")
    private int pinLength;

    @Value("${card.pin.length:4}")
    private int cardPinLength;

    @Value("${default.daily.limit:20000}")
    private BigDecimal defaultDailyLimit;

    @Value("${bank.accounts.operation.modes:Jointly, Singly}")
    private String[] accountOperationModes;

    @Value("${phone.number.length:11}")
    private int phoneNumberLength;

    @Value("${banks.cache.timeout.seconds:18000}")
    private int banksCacheTimeoutInSeconds;

    @Value("${max.device.count:5}")
    private int maxDeviceCount;

    @Value("${session.timeout.in.secs:600}")
    private int sessionTimeoutInSecs;

    @Value("${password.reset.code.timeout.in.secs:300}")
    private int passwordResetCodeTimeoutSecs;

    @Value("${secret.questions.timeout.in.secs:86400}")
    private int secretQuestionsTimeoutSecs;

    @Value("${max.failed.login.attempts:4}")
    private int maxFailedLoginAttempts;

    @Value("${institution.code:}")
    private String institutionCode;

    @Value("${bank.adapter.service.url:}")
    private String bankAdapterServiceUrl;

    @Value("${country.calling.code:+234}")
    private String countryCallingCode;

    @Value("${api.timeout.millis:60000}")
    private long apiTimeoutMillis;

    @Value("${email.mask.length:4}")
    private Integer emailMaskLength;

    @Value("${minAppVersionSupported:1.0.0}")
    private String minAppVersionSupported;

    @Value("${stopRequestBelowAppMinVersion:false}")
    private Boolean stopRequestBelowAppMinVersion;

    @Value("${redisUrl}")
    private String redisUrl;

    public boolean getIsDevOrTestEnv() {
        final BooleanSupplier isDevOrTestEnv = () -> Arrays.stream(currentProfile.split(","))
                .anyMatch(s -> s.equalsIgnoreCase("dev") || s.equalsIgnoreCase("test"));
        return isDevOrTestEnv.getAsBoolean();
    }
}
