package info.hexin.jmacs.aop.advisor;


/**
 * 实现通知无法修改方法的返回值，但是可以通过抛出异常阻止方法运行.
 * 
 * @author hexin
 * 
 */
public interface Advice {

    /**
     * 方法调用之前
     * 
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    void before(MethodInvoke invoke);

    /**
     * 方法调用之后，如果出现异常，那么异常优先
     * 
     * @param returnValue
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    void after(MethodInvoke invoke);

    /**
     * 执行方法出现异常
     * 
     * @param throwable
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    void whenException(MethodInvoke invoke);
}
