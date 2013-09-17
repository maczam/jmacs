package info.hexin.jmacs.ioc;

import info.hexin.jmacs.ioc.annotation.Bean;
import info.hexin.jmacs.ioc.annotation.Scope;
import info.hexin.lang.Exceptions;

import java.util.List;

/**
 * 
 * 定义bean 工厂东西
 * 
 * @author hexin
 * 
 */
public class IocBean {

    private String name;
    private String className;
    private Bean annotation;
    private Class<?> clazz;
    private Scope scope;
    private List<Property> propertiesList;
    private Object instance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Bean getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Bean annotation) {
        this.annotation = annotation;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setPropertiesList(List<Property> propertiesList) {
        this.propertiesList = propertiesList;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public synchronized Object getInstance() {
        return instance;
    }

    /**
     * 专门实例话新的实例
     * 
     * @return
     */
    public Object newInstance() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw Exceptions.make(clazz.getName() + " is interface or abstract class ??");
        } catch (IllegalAccessException e) {
            throw Exceptions.make(clazz.getName() + " Constructor is public ??");
        } catch (Exception e) {
            throw Exceptions.make(e);
        }
    }

    public boolean isSingleton() {
        return scope == Scope.singleton;
    }

    public List<Property> getPropertiesList() {
        return propertiesList;
    }
}
