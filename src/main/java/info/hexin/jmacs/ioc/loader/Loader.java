package info.hexin.jmacs.ioc.loader;

import info.hexin.jmacs.ioc.IocBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源加载
 * 
 * @author hexin
 * 
 */
public interface Loader {
    final Map<String, IocBean> beanMap = new HashMap<String, IocBean>();
    final Map<Class<?>, List<Class<?>>> iMap = new HashMap<Class<?>, List<Class<?>>>();

    /**
     * 返回加载素有类
     * 
     * @return
     */
    Map<String, IocBean> getBeans();

    Map<Class<?>, List<Class<?>>> getIMap();
}
