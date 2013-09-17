package info.hexin.jmacs.ioc.annotation;

/**
 * java bean的生命周期
 * 
 * @author hexin
 * 
 */
public enum Scope {
    singleton, prototype;

    public static Scope of(String scope) {
        if ("prototype".equals(scope)) {
            return prototype;
        } else {
            return singleton;
        }
    }
}
