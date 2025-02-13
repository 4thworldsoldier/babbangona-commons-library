package com.babbangona.commons.library.logging;

import java.util.regex.Matcher;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
;

public class CommonsLoggerImpl implements CommonsLogger {
    private final Logger logger;

    public CommonsLoggerImpl(Class<?> klass) {
        this.logger = ESAPI.getLogger(klass);
    }

    @Override
    public void info(String tag, String data) {
        logger.info(Logger.EVENT_UNSPECIFIED, String.format("<< %s : %s >>", tag.toUpperCase(), data));
    }

    @Override
    public void info(String tag, Object... params) {
        tag = tag.toUpperCase();
        for (Object object : params) {
            tag = tag.replaceFirst("\\{\\}", Matcher.quoteReplacement(String.valueOf(object)));
        }
        logger.info(Logger.EVENT_UNSPECIFIED, String.format("<< %s >>", tag));
    }

    @Override
    public void info(String tag, Throwable t) {
        logger.info(Logger.EVENT_UNSPECIFIED, String.format("<< %s : %s >>", tag.toUpperCase(), t));
    }

    @Override
    public void info(String tag, Throwable t, Object... params) {
        tag = tag.toUpperCase();
        for (Object object : params) {
            tag = tag.replaceFirst("\\{\\}", Matcher.quoteReplacement(String.valueOf(object)));
        }
        logger.info(Logger.EVENT_UNSPECIFIED, String.format("<< %s >>", tag), t);
    }

    @Override
    public void debug(String tag, String data) {
        logger.debug(Logger.EVENT_UNSPECIFIED, String.format("<< %s : %s >>", tag.toUpperCase(), data));
    }

    @Override
    public void debug(String tag, Object... params) {
        tag = tag.toUpperCase();
        for (Object object : params) {
            tag = tag.replaceFirst("\\{\\}", Matcher.quoteReplacement(String.valueOf(object)));
        }
        logger.debug(Logger.EVENT_UNSPECIFIED, String.format("<< %s >>", tag));
    }

    @Override
    public void debug(String tag, Throwable t) {
        logger.debug(Logger.EVENT_UNSPECIFIED, String.format("<< %s : %s >>", tag.toUpperCase(), t));
    }

    @Override
    public void debug(String tag, Throwable t, Object... params) {
        tag = tag.toUpperCase();
        for (Object object : params) {
            tag = tag.replaceFirst("\\{\\}", Matcher.quoteReplacement(String.valueOf(object)));
        }
        logger.debug(Logger.EVENT_UNSPECIFIED, String.format("<< %s >>", tag), t);
    }

    @Override
    public void error(String tag, String data) {
        logger.error(Logger.EVENT_FAILURE, String.format("<< %s : %s >>", tag.toUpperCase(), data));
    }

    @Override
    public void error(String tag, Object... params) {
        tag = tag.toUpperCase();
        for (Object object : params) {
            tag = tag.replaceFirst("\\{\\}", Matcher.quoteReplacement(String.valueOf(object)));
        }
        logger.error(Logger.EVENT_FAILURE, String.format("<< %s >>", tag));
    }

    @Override
    public void error(String tag, Throwable t) {
        logger.error(Logger.EVENT_FAILURE, String.format("<< %s : %s >>", tag.toUpperCase(), t));
    }

    @Override
    public void error(String tag, Throwable t, Object... params) {
        tag = tag.toUpperCase();
        for (Object object : params) {
            tag = tag.replaceFirst("\\{\\}", Matcher.quoteReplacement(String.valueOf(object)));
        }
        logger.error(Logger.EVENT_FAILURE, String.format("<< %s >>", tag), t);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }
}
