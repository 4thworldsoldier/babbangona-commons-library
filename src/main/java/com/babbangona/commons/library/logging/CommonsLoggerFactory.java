package com.babbangona.commons.library.logging;

public class CommonsLoggerFactory {

    public static CommonsLogger getLogger(Class<?> klass) {
        return new CommonsLoggerImpl(klass);
    }
}
