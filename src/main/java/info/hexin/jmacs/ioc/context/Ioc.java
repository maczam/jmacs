package info.hexin.jmacs.ioc.context;

import info.hexin.jmacs.ioc.IocBean;

import java.util.Map;

/**
 * ioc的context容器
 * 
 * @author hexin
 * 
 */
public interface Ioc {

    <T> T getBean(Class<T> clazz);

    boolean containsBean(String name);

    boolean isSingleton(String name);

    void load(Ioc parent);

    Map<String, IocBean> getIocBeans();

    Object getBean(String beanId);
}
