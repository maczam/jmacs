package info.hexin.jmacs.log;

public interface Log {
    
    /// 等级最低
    boolean isTraceEnabled();

    void trace(Object message);

    void trace(String fmt, Object... args);

    void trace(Object message, Throwable t);
    

    //debug
    boolean isDebugEnabled();

    void debug(Object message);

    void debug(String fmt, Object... args);

    void debug(Object message, Throwable t);
    
    //info
    boolean isInfoEnabled();

    void info(Object message);

    void info(String fmt, Object... args);

    void info(Object message, Throwable t);
    
    //warn
    boolean isWarnEnabled();

    void warn(Object message);

    void warn(String fmt, Object... args);

    void warn(Object message, Throwable t);
    
    //error
    boolean isErrorEnabled();

    void error(Object message);

    void error(String fmt, Object... args);

    void error(Object message, Throwable t);
}
