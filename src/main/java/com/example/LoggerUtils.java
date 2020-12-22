package com.example;

import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.Arrays;

public class LoggerUtils {

	public static Logger createAppLogger(String name) {
		Appender<ILoggingEvent> stdout = createConsoleAppender("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
		Appender<ILoggingEvent> console = createConsoleAppender("%d{HH:mm:ss.SSS} [%thread] %-5level %replace(%replace(%caller{1..2}){'Caller\\+\\d+\\s+at\\s+', ''}){'\\r?\\n', ''} - %msg%n");
		Appender<ILoggingEvent> allLogsFile = createFileAppender();

		Logger appLogger = createLogger(name, Level.DEBUG, new Appender[] { console, allLogsFile });
		appLogger.info("info test");
		appLogger.debug("debug test");

		return appLogger;
	}

	private static ConsoleAppender<ILoggingEvent> createConsoleAppender(String pattern) {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		PatternLayoutEncoder ple = new PatternLayoutEncoder();

		ple.setPattern(pattern);
		ple.setContext(lc);
		ple.start();
		ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
		consoleAppender.setEncoder(ple);
		consoleAppender.setContext(lc);
		consoleAppender.start();

		return consoleAppender;
	}

	private static RollingFileAppender<ILoggingEvent> createFileAppender() {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		PatternLayoutEncoder ple = new PatternLayoutEncoder();

		ple.setPattern("%d [%thread] %-5level %replace(%replace(%caller{1..2}){'Caller\\+\\d+\\s+at\\s+', ''}){'\\r?\\n', ''} - %msg%n");
		ple.setContext(lc);
		ple.start();
		RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<ILoggingEvent>();
		fileAppender.setFile("logs/all.log");
		fileAppender.setEncoder(ple);
		fileAppender.setContext(lc);
		//todo rolling policy
		fileAppender.start();

		return fileAppender;
	}

	private static Logger createLogger(String name, Level level, Appender<ILoggingEvent>[] appenders) {
		Logger logger = (Logger) LoggerFactory.getLogger(name);
		Arrays.stream(appenders).forEach(logger::addAppender);
		logger.setLevel(level);
		logger.setAdditive(false);
		return logger;
	}

}
