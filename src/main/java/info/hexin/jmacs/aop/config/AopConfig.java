package info.hexin.jmacs.aop.config;

import info.hexin.jmacs.aop.advisor.Advice;
import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.lang.Exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author hexin
 * 
 */
public abstract class AopConfig {
    public static final List<Class<?>> aopClassList = new ArrayList<Class<?>>();
    private static Ioc ioc = null;

    public static void setIoc(Ioc ioc) {
        if (ioc != null) {
            AopConfig.ioc = ioc;
        }
    }

    /**
     * 
     * @param advice
     * @return
     */
    public static Advice getAopBean(String advice) {
        Object adviceBean = ioc.getBean(advice);
        if (adviceBean instanceof Advice) {
            return (Advice) adviceBean;
        } else {
            throw Exceptions.make(String.format("%s 没有找到要注入的Aop类！", advice));
        }
    }
}
