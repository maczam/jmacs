package info.hexin.jmacs.log.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.LogAdapter;

/**
 * jdk自带的log
 * 
 * @author hexin
 * 
 */
public class CommonLogAdapter implements LogAdapter {

    @Override
    public Log getLogger(String className) {
        return new Commonloger(className);
    }
}

class Commonloger implements Log {

    private Logger logger;

    public Commonloger(String className) {
        logger = Logger.getLogger(className);
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isLoggable(LogLevel.TRACE.value());
    }

    @Override
    public void trace(Object message) {
        logger.log(LogLevel.TRACE.value(), message.toString());
    }

    @Override
    public void trace(String fmt, Object... args) {
        logger.log(LogLevel.TRACE.value(), fmt, args);
    }

    @Override
    public void trace(Object message, Throwable t) {
        logger.log(LogLevel.TRACE.value(), message.toString(), t);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isLoggable(LogLevel.DEBUG.value());
    }

    @Override
    public void debug(Object message) {
//        logger.log(LogLevel.DEBUG.value(),message.toString());
        logger.config(message.toString());
    }

    @Override
    public void debug(String fmt, Object... args) {
        logger.log(LogLevel.DEBUG.value(), fmt, args);

    }

    @Override
    public void debug(Object message, Throwable t) {
        logger.log(LogLevel.DEBUG.value(), message.toString(), t);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isLoggable(LogLevel.INFO.value());
    }

    @Override
    public void info(Object message) {
        logger.info(message.toString());
    }

    @Override
    public void info(String fmt, Object... args) {
        logger.log(LogLevel.INFO.value(), fmt, args);

    }

    @Override
    public void info(Object message, Throwable t) {
        logger.log(LogLevel.INFO.value(), message.toString(), t);

    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isLoggable(LogLevel.WARN.value());
    }

    @Override
    public void warn(Object message) {
        logger.log(LogLevel.WARN.value(), message.toString());

    }

    @Override
    public void warn(String fmt, Object... args) {
        logger.log(LogLevel.WARN.value(), fmt, args);
    }

    @Override
    public void warn(Object message, Throwable t) {
        logger.log(LogLevel.WARN.value(), message.toString(), t);

    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isLoggable(LogLevel.ERROR.value());
    }

    @Override
    public void error(Object message) {
        logger.log(LogLevel.ERROR.value(), message.toString());

    }

    @Override
    public void error(String fmt, Object... args) {
        logger.log(LogLevel.ERROR.value(), fmt, args);

    }

    @Override
    public void error(Object message, Throwable t) {
        logger.log(LogLevel.ERROR.value(), message.toString(), t);

    }

}

/**
 * 日志级别,基本和logging对应
 * 
 * @author hexin
 * 
 */
enum LogLevel {

    TRACE(Level.FINE), DEBUG(Level.CONFIG), INFO(Level.INFO), WARN(Level.WARNING), ERROR(Level.SEVERE), FATAL(Level.ALL);
    private LogLevel(Level level) {
        this.level = level;
    }

    public Level value() {
        return level;
    }

    private Level level;
}
