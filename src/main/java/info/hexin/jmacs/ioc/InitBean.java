package info.hexin.jmacs.ioc;

/**
 * 需要调用初始化方法
 * 
 * @author hexin
 * 
 */
public interface InitBean {

    /**
     * 在放入参数之后调用
     * 
     * @throws Exception
     */
    void afterPropertiesSet();
}
