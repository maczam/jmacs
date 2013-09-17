package info.hexin.jmacs.ioc.loader.impl;

import info.hexin.jmacs.aop.annotation.Aop;
import info.hexin.jmacs.ioc.IocBean;
import info.hexin.jmacs.ioc.annotation.Bean;
import info.hexin.jmacs.ioc.loader.Loader;
import info.hexin.jmacs.mvc.annotation.Action;
import info.hexin.jmacs.mvc.annotation.Interceptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractLoader implements Loader {
    static final List<Class<? extends Annotation>> supportAnnotation = new ArrayList<Class<? extends Annotation>>();

    static {
        supportAnnotation.add(Aop.class);
        supportAnnotation.add(Bean.class);
        supportAnnotation.add(Action.class);
        supportAnnotation.add(Interceptor.class);
    }

    @Override
    public Map<String, IocBean> getBeans() {
        return beanMap;
    }

    @Override
    public Map<Class<?>, List<Class<?>>> getIMap() {
        return iMap;
    }

    void loadInterFace(Class<?> clazz) {
        Class<?>[] inClasses = clazz.getInterfaces();
        for (Class<?> c : inClasses) {
            if (iMap.containsKey(c)) {
                iMap.get(c).add(clazz);
            } else {
                List<Class<?>> lsit = new ArrayList<Class<?>>();
                lsit.add(clazz);
                iMap.put(c, lsit);
            }
        }
    }

    List<Class<?>> getImplClass(Class<?> interfacz) {
        return iMap.get(interfacz);
    }
}
