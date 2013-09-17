package info.hexin.lang;

/**
 * 
 * @author hexin
 * 
 */
public class Exceptions {

    /**
     * 生成一个RuntimeException
     * 
     * @param msg
     * @return
     */
    public static RuntimeException make(String msg) {
        return new RuntimeException(msg);
    }
    
    /**
     * 生成一个RuntimeException
     * 
     * @param msg
     * @return
     */
    public static RuntimeException make() {
        return new RuntimeException();
    }

    /**
     * 将异常包裹为一个运行时异常
     * 
     * @param msg
     * @return
     */
    public static RuntimeException make(Throwable e) {
        return new RuntimeException(e);
    }

    public static RuntimeException make(String msg, Throwable e) {
        return new RuntimeException(msg,e);
    }
}
