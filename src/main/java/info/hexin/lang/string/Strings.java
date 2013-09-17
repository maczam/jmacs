package info.hexin.lang.string;

/**
 * String 操作类
 * 
 * @author ydhexin@gmail.com
 * 
 */
public class Strings {
    /**
     * <p>
     * <li>null === true
     * <li>"" === true
     * <li>" " === false
     * </p>
     * 
     * @param s
     * @return
     */
    public static boolean isNull(String s) {
        return null == s || s.length() == 0;
    }

    public static String notNull(String s) {
        return (null == s || "null".equalsIgnoreCase(s)) ? "" : s;
    }

    /**
     * <p>
     * <li>null === false
     * <li>"" === false
     * <li>" " === true
     * </p>
     * 
     * @param s
     * @return
     */
    public static boolean isNotNull(String s) {
        return null != s && s.length() > 0;
    }

    public static boolean isBlank(String s) {
        return null == s || "null".equalsIgnoreCase(s) || "\nnull".equals(s) || s.trim().length() == 0;
    }

    public static boolean isBlank(Object s) {
        return null == s || s.toString().trim().length() == 0;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    public static boolean isNotBlankJson(String s) {
        return isNotBlank(s) && !s.replaceAll(" ", "").equalsIgnoreCase("{}");
    }

    public static boolean isNotBlank(Object s) {
        return null != s && s.toString().trim().length() > 0;
    }

    public static String of(Object o) {
        return isBlank(o) ? "" : o.toString();
    }

    /**
     * 将首字母转化成小写
     * 
     * @param str
     * @return
     */
    public static String lowerFirst(String str) {
        // char first = str.charAt(0);
        // String lowerCastString =
        // String.valueOf(Character.toLowerCase(first));
        // return lowerCastString+str.substring(1);
        char[] array = str.toCharArray();
        array[0] += 32;
        return String.valueOf(array);

    }

    /**
     * 将首字母转化成大写
     * 
     * @param str
     * @return
     */
    public static String upperFirst(String str) {
        char[] array = str.toCharArray();
        array[0] -= 32;
        return String.valueOf(array);
    }

    public static int toInt(String i) {
        return Integer.valueOf(i);
    }

    public static boolean toBoolean(String s) {
        return Boolean.parseBoolean(s);
    }

    public static void main(String[] args) {
        System.out.println(lowerFirst("Aaaaa"));
    }
}
