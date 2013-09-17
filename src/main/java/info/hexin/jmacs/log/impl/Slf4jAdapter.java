package info.hexin.jmacs.log.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.LogAdapter;

/**
 * Slf4j 已经包含了大部分的log
 * 
 * @author hexin
 * 
 */
public class Slf4jAdapter implements LogAdapter {

    @Override
    public Log getLogger(String className) {
        return new Slf4jloger(className);
    }
}

class Slf4jloger implements Log {

    private Logger logger;

    public Slf4jloger(String className) {
        logger = LoggerFactory.getLogger(className);
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public void trace(Object message) {
        logger.trace(message.toString());
    }

    @Override
    public void trace(String fmt, Object... args) {
        logger.trace(fmt, args);
    }

    @Override
    public void trace(Object message, Throwable t) {
        logger.trace(message.toString(), t);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(Object message) {
        logger.debug(message.toString());

    }

    @Override
    public void debug(String fmt, Object... args) {
        logger.debug(fmt, args);

    }

    @Override
    public void debug(Object message, Throwable t) {
        logger.debug(message.toString(), t);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(Object message) {
        logger.info(message.toString());

    }

    @Override
    public void info(String fmt, Object... args) {
        logger.info(fmt, args);

    }

    @Override
    public void info(Object message, Throwable t) {
        logger.info(message.toString(), t);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void warn(Object message) {
        logger.warn(message.toString());

    }

    @Override
    public void warn(String fmt, Object... args) {
        logger.warn(fmt, args);
    }

    @Override
    public void warn(Object message, Throwable t) {
        logger.warn(message.toString(), t);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void error(Object message) {
        logger.error(message.toString());
    }

    @Override
    public void error(String fmt, Object... args) {
        logger.error(fmt, args);
    }

    @Override
    public void error(Object message, Throwable t) {
        logger.error(message.toString(), t);
    }
}