package com.babbangona.commons.library.logging;

public interface CommonsLogger {
    void info(String tag, String data);

    void info(String tag, Object... params);

    void info(String tag, Throwable t);

    void info(String tag, Throwable t, Object... params);

    void debug(String tag, String data);

    void debug(String tag, Object... params);

    void debug(String tag, Throwable t);

    void debug(String tag, Throwable t, Object... params);

    void error(String tag, String data);

    void error(String tag, Object... params);

    void error(String tag, Throwable t);

    void error(String tag, Throwable t, Object... params);

    boolean isDebugEnabled();
}
