package info.hexin.jmacs.aop;

import info.hexin.jmacs.aop.annotation.Point;
import info.hexin.lang.Exceptions;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author hexin
 * 
 */
public class Aops {

    /**
     * 是否包含带有aop的方法
     * 
     * @param type
     * @return
     */
    public static boolean containAopMethod(Class<?> type) {
        Method[] xx = type.getDeclaredMethods();
        for (Method method : xx) {
            Point point = method.getAnnotation(Point.class);
            if (point != null) {
                if (!Modifier.isFinal(method.getModifiers()) && !Modifier.isAbstract(method.getModifiers())
                        && point.value() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取所有的带有aop注解方法。 方法必须不是final abstract
     * 
     * @param clazz
     * @return
     */
    public static List<Method> getAopMethods(Class<?> clazz) {
        List<Method> list = new ArrayList<Method>();
        Method[] xx = clazz.getDeclaredMethods();
        for (Method method : xx) {
            Point point = method.getAnnotation(Point.class);
            if (point != null && point.value() != null) {
                if (Modifier.isFinal(method.getModifiers()) || Modifier.isAbstract(method.getModifiers())) {
                    throw Exceptions.make("Point method 不能为final 也不能为Abstract！！！");
                } else {
                    list.add(method);
                }
            }
        }
        return list;
    }

    public static boolean needOverride(Method m) {
        // object类本身的方法不做重写
        if (m.getDeclaringClass().getName().equals(Object.class.getName())) {
            return false;
        }
        // "main" 方法不做重写
        if (Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers())
                && m.getReturnType().getName().equals("void") && m.getName().equals("main")) {
            return false;
        }
        return true;
    }
}
