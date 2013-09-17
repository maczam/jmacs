package info.hexin.jmacs.ioc.loader.impl;

import info.hexin.jmacs.aop.annotation.Aop;
import info.hexin.jmacs.ioc.IocBean;
import info.hexin.jmacs.ioc.Iocs;
import info.hexin.jmacs.ioc.Property;
import info.hexin.jmacs.ioc.annotation.Bean;
import info.hexin.jmacs.ioc.annotation.Scope;
import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.jmacs.mvc.annotation.Action;
import info.hexin.jmacs.mvc.annotation.Interceptor;
import info.hexin.lang.Exceptions;
import info.hexin.lang.string.Strings;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 解析已经标记注解的类
 * 
 * @author hexin
 * 
 */
public class AnnotationLoader extends AbstractLoader {
    static Log log = Logs.get();

    public AnnotationLoader(String... packages) {
        loadPackages(packages);
    }

    /**
     * 找到所有注解的类, 并实现单例
     * 
     * @param packages
     */
    private void loadPackages(String... packages) {
        Set<Class<?>> classSet = loadClass(packages);
        for (Class<?> clazz : classSet) {
            IocBean iocBean = new IocBean();
            String name = null;
            for (Class<? extends Annotation> annotationClass : supportAnnotation) {
                Annotation bean = clazz.getAnnotation(annotationClass);
                if (bean instanceof Aop) {
                    name = ((Aop) bean).name();
                    if (Strings.isBlank(name)) {
                        name = Strings.lowerFirst(clazz.getSimpleName());
                    }
                    iocBean.setScope(Scope.singleton);
                } else if (bean instanceof Bean) {
                    name = ((Bean) bean).name();
                    if (Strings.isBlank(name)) {
                        name = Strings.lowerFirst(clazz.getSimpleName());
                    }
                    Scope scope = ((Bean) bean).scope();
                    iocBean.setScope(scope);
                } else if (bean instanceof Action || bean instanceof Interceptor) {
                    name = Strings.lowerFirst(clazz.getSimpleName());
                    iocBean.setScope(Scope.singleton);
                }
            }
            iocBean.setName(name);
            iocBean.setClazz(clazz);
            iocBean.setClassName(clazz.getName());
            beanMap.put(name, iocBean);
            log.info("name >>>>> " + iocBean.getName() + " 增加到context中");
            List<Property> propertiesList = Iocs.getPropertiesList(clazz);
            iocBean.setPropertiesList(propertiesList);
        }
    }

    /**
     * 装载class
     * 
     * @param packages
     * @return
     * @throws ClassNotFoundException
     */
    Set<Class<?>> loadClass(String... packages) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (String packagePath : packages) {
            log.info(" 将要加载包 >>>>> " + packagePath);
            String filePath = ClassLoader.getSystemResource("").getPath() + packagePath.trim().replace(".", "\\");
            File file = new File(filePath);
            if (!file.exists()) {
                throw Exceptions.make("packagePath >>> " + packagePath + " is not exists!!");
            }
            String[] ss = file.list();
            for (String fileName : ss) {
                if (fileName.endsWith(".class")) {
                    String clazzName = packagePath + "." + fileName.substring(0, fileName.indexOf("."));
                    Class<?> clazz;
                    try {
                        clazz = Class.forName(clazzName);
                        for (Class<? extends Annotation> annotationClass : supportAnnotation) {
                            Annotation bean = clazz.getAnnotation(annotationClass);
                            if (bean != null) {
                                loadInterFace(clazz);
                                classSet.add(clazz);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classSet;
    }
}
