package com.example.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {

    private static ThreadLocal<Logger> loggers = new ThreadLocal<>() {
        @Override
        protected Logger initialValue() {
            return LoggerFactory.getLogger(AppLogger.class.getName() + "." + Thread.currentThread().getName());
        };
    };

    private AppLogger() {}

    private static Logger getLogger() {
        return loggers.get();
    }

    public static void trace(String message) {
        getLogger().trace(message);
    }

    public static void debug(String message) {
        getLogger().debug(message);
    }

    public static void info(String message) {
        getLogger().info(message);
    }

    public static void warn(String message) {
        getLogger().warn(message);
    }

    public static void error(String message) {
        getLogger().error(message);
    }
}
