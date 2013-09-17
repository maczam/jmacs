package info.hexin.jmacs.ioc.context.impl;

import info.hexin.jmacs.aop.config.AopConfig;
import info.hexin.jmacs.ioc.InitBean;
import info.hexin.jmacs.ioc.IocBean;
import info.hexin.jmacs.ioc.annotation.Bean;
import info.hexin.jmacs.ioc.annotation.Scope;
import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.ioc.loader.Loader;
import info.hexin.jmacs.ioc.proxy.DefaultProxyFactory;
import info.hexin.jmacs.ioc.proxy.ProxyFactory;
import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.lang.Exceptions;
import info.hexin.lang.reflect.Reflects;
import info.hexin.lang.string.Strings;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 统一抽象context加载
 * 
 * @author hexin
 * 
 */
public class SimpleIoc implements Ioc {
    
    private static Log log = Logs.get();

    private Map<String, IocBean> beans;
    private Map<Class<?>, List<Class<?>>> iMapBeans;
    private ProxyFactory proxyFactory;

    public SimpleIoc(Loader resourceLoader) {
        this.beans = resourceLoader.getBeans();
        this.iMapBeans = resourceLoader.getIMap();
        this.proxyFactory = new DefaultProxyFactory(beans);

        // initbean
        initbean();

        // 下面一定需要，保存一份ioc指引。如果写道别的地方，那么每次newioc的时候都需要修改
        AopConfig.setIoc(this);
    }

    private void initbean() {
        for (Map.Entry<String, IocBean> entry : beans.entrySet()) {
            IocBean iocBean = entry.getValue();
            if (iocBean.getScope() == Scope.singleton) {
                
                log.debug("init bean >>>"+iocBean.getClazz());
                
                Object instancebean = iocBean.newInstance();
                iocBean.setInstance(instancebean);
                Reflects.injectField(instancebean, iocBean, this);
                try {
                    if(InitBean.class.isAssignableFrom(iocBean.getClazz())){
                        ((InitBean)instancebean).afterPropertiesSet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 先尝试获取代理类，在按照真实名字访问
     * 
     * @param clazz
     * @param beanName
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T getBean(Class<T> clazz, String beanName) {
        IocBean iocBean = null;
        if (clazz == null) {
            iocBean = beans.get(beanName);
        } else {
            if (clazz.isInterface()) {
                List<Class<?>> classList = iMapBeans.get(clazz);
                if (classList != null) {
                    for (Class<?> class1 : classList) {
                        iocBean = getIocBean(class1, beanName);
                        if (iocBean != null) {
                            break;
                        }
                    }
                }
                // 如果传进来的class为两层接口的话，默认在Imap中没有保存，尝试用接口的名称获取
                if (iocBean == null) {
                    iocBean = beans.get(beanName);
                }
            } else {
                iocBean = getIocBean(clazz, beanName);
            }
        }

        if (iocBean != null) {
            try {
                T instancebean = null;
                if (iocBean.isSingleton()) {
                    instancebean = (T) iocBean.getInstance();
                } else {
                    instancebean = (T) iocBean.newInstance();
                    Reflects.injectField(instancebean, iocBean, this);
                    try {
                        if(InitBean.class.isAssignableFrom(iocBean.getClazz())){
                            ((InitBean)instancebean).afterPropertiesSet();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return instancebean;
            } catch (Exception e) {
                throw Exceptions.make("beanName >>> " + beanName + " 实例获取异常!!", e);
            }
        }
        return null;
    }

    private <T> IocBean getIocBean(Class<T> clazz, String beanName) {
        Class<?> proxyClass = proxyFactory.getProxyIocBean(clazz);
        if (proxyClass != null) {
            beanName = Strings.lowerFirst(proxyClass.getSimpleName());
        }
        return beans.get(beanName);
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        Bean bean = clazz.getAnnotation(Bean.class);
        String beanId;
        if (bean != null && Strings.isNotBlank(bean.name())) {
            beanId = bean.name();
        } else {
            beanId = Strings.lowerFirst(clazz.getSimpleName());
        }
        return (T) this.getBean(clazz, beanId);
    }

    @Override
    public boolean containsBean(String name) {
        return beans.containsKey(name);
    }

    @Override
    public boolean isSingleton(String name) {
        ensureContains(name);
        IocBean bean = beans.get(name);
        return Scope.singleton == bean.getScope();
    }

    @Override
    public void load(Ioc parent) {
        if (parent == null) {
            return;
        }
        this.beans.putAll(parent.getIocBeans());
    }

    @Override
    public Map<String, IocBean> getIocBeans() {
        return Collections.unmodifiableMap(beans);
    }

    /**
     * 没有找到，抛出异常
     * 
     * @param beanName
     */
    private void ensureContains(String beanName) {
        if (!containsBean(beanName)) {
            throw Exceptions.make("beanName >>> " + beanName + "  没有找到!!");
        }
    }

    @Override
    public Object getBean(String beanId) {
        return this.getBean(null, beanId);
    }
}
