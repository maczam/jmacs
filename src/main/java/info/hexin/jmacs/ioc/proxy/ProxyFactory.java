package info.hexin.jmacs.ioc.proxy;

/**
 * 判断是否需要代理
 * 
 * @author hexin
 * 
 */
public interface ProxyFactory {

    Class<?> getProxyIocBean(Class<?> type);
}
