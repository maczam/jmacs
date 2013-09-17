package info.hexin.jmacs.aop.model;

import info.hexin.jmacs.aop.advisor.Advice;
import info.hexin.jmacs.aop.advisor.MethodInvoke;
import info.hexin.jmacs.aop.annotation.Aop;
import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;

import java.lang.reflect.Method;

/**
 * 测试事务拦截器
 * 
 * @author hexin
 * 
 */
@Aop
public class Tx implements Advice {
    Log log = Logs.get();

    @Override
    public void before(MethodInvoke invoke) {
        log.info("before");
        if(invoke.getArgs() != null){
            log.info("invoke >>>>> " + invoke.getArgs().length);
        }
    }

    @Override
    public void after(MethodInvoke invoke) {
        log.info("after");
    }

    @Override
    public void whenException(MethodInvoke invoke) {
        log.info("whenException",invoke.getThrowable());
    }
}
