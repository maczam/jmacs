package info.hexin.lang;


/**
 * 断言的封装
 * 
 * @author hexin
 * 
 */
public class Assert {

    public static void equals(String expected, String actual) {
        equals(null, expected, actual);
    }

    static void equals(String message, Object expected, Object actual) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        }
        fail(format(message, expected, actual));
    }

    static void fail(String message) {
        if (message == null) {
            throw Exceptions.make();
        }
        throw Exceptions.make(message);
    }

    static String format(String message, Object expected, Object actual) {
        String formatted = "";
        if (message != null && message.length() > 0) {
            formatted = message + " ";
        }
        return formatted + "expected: " + expected + "  but was: " + actual + " ";
    }

    /**
     * 判断是否为null，如果为null的话抛出异常
     * 
     * @param object
     */
    public static void notNull(Object object) {
        if (object == null) {
            throw Exceptions.make("NOT NULL!!!");
        }
    }

    public static void isTrue(boolean flag) {
        if (!flag) {
            throw Exceptions.make("must true!!!");
        }
    }
}
