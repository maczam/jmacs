package info.hexin.lang.reflect;

/**
 * class 部分工具
 * 
 * @author hexin
 * 
 */
public abstract class Klass {

    /**
     * 判断klass 是不是interFaceClass接口的实现类
     * 
     * @param klass
     * @param interFaceClass
     * @return
     */
    public static boolean isInterfaceFrom(Class<?> klass, Class<?> interFaceClass) {
        if (klass.isInterface()) {
            return false;
        }

        while (klass != null) {
            Class<?>[] interfaces = klass.getInterfaces();
            for (Class<?> type : interfaces) {
                if (interFaceClass == type) {
                    return true;
                }
            }
            klass = klass.getSuperclass();
        }
        return false;
    }
}
