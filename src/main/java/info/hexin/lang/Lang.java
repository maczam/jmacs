package info.hexin.lang;

/**
 * 一些常用工具
 * 
 * @author hexin
 * 
 */
public class Lang {

    /**
     * 
     * 
     * @param object
     * @return
     */
    public static boolean isNull(Object object) {
        if (object == null) {
            return true;
        } else if (object.getClass() == String.class) {
            return "".equals(object);
        }
        return false;
    }

    public static int toInt(Object object) {
        return Integer.valueOf(object.toString());
    }

    public static long toLong(Object object) {
        return Long.valueOf(object.toString());
    }

    public static boolean toBoolean(Object object) {
        return Boolean.parseBoolean(object.toString());
    }

    public static byte toByte(Object object) {
        return Byte.valueOf(object.toString());
    }

    public static char toChar(Object object) {
        String valueString = object.toString();
        return valueString.charAt(0);
    }

    public static double toDouble(Object object) {
        return Double.valueOf(object.toString());
    }

    public static float toFloat(Object object) {
        return Float.valueOf(object.toString());
    }

    public static short toShort(Object object) {
        return Short.valueOf(object.toString());
    }
}
