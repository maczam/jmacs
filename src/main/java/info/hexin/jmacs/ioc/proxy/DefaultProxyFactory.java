package info.hexin.jmacs.ioc.proxy;

import info.hexin.jmacs.aop.Aops;
import info.hexin.jmacs.aop.proxy.AopProxy;
import info.hexin.jmacs.aop.proxy.asm.AsmAopProxy;
import info.hexin.jmacs.ioc.IocBean;
import info.hexin.jmacs.ioc.Iocs;
import info.hexin.jmacs.ioc.annotation.Bean;
import info.hexin.lang.string.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * aop连接点
 * 
 * @author hexin
 * 
 */
public class DefaultProxyFactory implements ProxyFactory {

    private Map<Class<?>, Class<?>> proxyClassMap = new HashMap<Class<?>, Class<?>>();
    private AopProxy asmAopProxy = new AsmAopProxy();
    private Map<String, IocBean> beans;

    public DefaultProxyFactory(Map<String, IocBean> beans) {
        this.beans = beans;
    }

    @Override
    public Class<?> getProxyIocBean(Class<?> type) {
        if (proxyClassMap.containsKey(type)) {
            return proxyClassMap.get(type);
        }

        if (Aops.containAopMethod(type)) {
            Class<?> proxyClass = asmAopProxy.getProxy(type);
            proxyClassMap.put(type, proxyClass);
            IocBean iocBean = new IocBean();
            iocBean.setAnnotation(type.getAnnotation(Bean.class));
            iocBean.setClassName(Strings.lowerFirst(proxyClass.getName()));
            iocBean.setClazz(proxyClass);
            iocBean.setPropertiesList(Iocs.getPropertiesList(proxyClass));
            beans.put(Strings.lowerFirst(proxyClass.getSimpleName()), iocBean);
            return proxyClass;
        }
        return type;
    }
}
