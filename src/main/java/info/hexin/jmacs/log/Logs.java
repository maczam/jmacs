package info.hexin.jmacs.log;

import info.hexin.jmacs.log.impl.CommonLogAdapter;
import info.hexin.jmacs.log.impl.Slf4jAdapter;

/**
 * 获取当前log的实例, 初始化日志的顺序slf4j>log4j>util.logging
 * 
 * @author hexin
 * 
 */
public class Logs {

    private static LogAdapter logAdapter;

    static {
        try {
            Class.forName("org.slf4j.impl.StaticLoggerBinder");
            logAdapter = new Slf4jAdapter();
        } catch (ClassNotFoundException e) {
            logAdapter = new CommonLogAdapter();
        }
    }

    public static Log get() {
        return logAdapter.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }
    
    public static Log get(String className) {
        return logAdapter.getLogger(className);
    }
    
    public static Log get(Class<?> clazz) {
        return logAdapter.getLogger(clazz.getName());
    }
}
