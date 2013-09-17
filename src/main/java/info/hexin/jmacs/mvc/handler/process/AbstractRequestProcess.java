package info.hexin.jmacs.mvc.handler.process;

import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.jmacs.mvc.config.RequestContext;
import info.hexin.jmacs.mvc.model.MappedInfo;
import info.hexin.jmacs.mvc.model.MethodInfo;
import info.hexin.jmacs.mvc.model.ViewInfo;
import info.hexin.jmacs.mvc.view.ViewModel;
import info.hexin.jmacs.mvc.view.resolver.CombViewResolver;
import info.hexin.jmacs.mvc.view.resolver.ViewResolver;
import info.hexin.lang.Exceptions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author hexin
 * 
 */
public abstract class AbstractRequestProcess {
    private static final Log logger = Logs.get();
    private ViewResolver viewResolver;

    /**
     * 处理request主要入口，一般不需要覆盖，覆盖的话就会导致混乱
     */
    public ViewModel process(RequestContext requestContext) throws IOException {

        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        Ioc ioc = requestContext.getIoc();
        MappedInfo mappedInfo = requestContext.getMappedInfo();

        MethodInfo methodInfo = mappedInfo.getMethodInfo();
        // 从request中封装参数
        List<Object> argsValue = doMethodParam(request, response, methodInfo, mappedInfo.getUrlParamMap());
        return getViewModel(ioc, request, response, methodInfo, argsValue);
    }

    /**
     * 
     * 根据request和response封装method需要的参数，并注入到Method中
     * 
     * @param request
     * @param response
     * @param args
     *            所需要参数列表
     * @param argList
     *            参数值对象，method反射调用传进去的参数列表
     */
    public abstract List<Object> doMethodParam(HttpServletRequest request, HttpServletResponse response,
            MethodInfo methodInfo, Map<String, String> urlParamMap) throws IOException;

    /**
     * 
     * @param ioc
     * @param request
     * @param response
     * @param methodInfo
     * @param argList
     * @return
     */
    public ViewModel getViewModel(Ioc ioc, HttpServletRequest request, HttpServletResponse response,
            MethodInfo methodInfo, List<Object> argList) {
        java.lang.reflect.Method reflectMethod = methodInfo.getReflectMethod();
        Class<?> actionClass = methodInfo.getClazz();
        Object actionInstance = ioc.getBean(actionClass);
        try {
            Object result = reflectMethod.invoke(actionInstance, argList.toArray());

            // 返回的是view
            if (result instanceof ViewModel) {
                logger.error(" 返回值为view");
                return (ViewModel) result;
            }
            // 声称view
            ViewResolver viewResolver = getViewResolver(ioc);
            ViewInfo viewInfo = methodInfo.getViewInfo();
            return viewResolver.make(viewInfo, result, ioc);
        } catch (Exception e) {
            logger.error("反射调用出现异常...");
            if (e instanceof InvocationTargetException) {
                InvocationTargetException e1 = (InvocationTargetException) e;
                throw Exceptions.make(e1.getTargetException());
            } else {
                throw Exceptions.make(e);
            }
        }
    }

    /**
     * 获取视图处理器，声称返回视图
     * 
     * @param ioc
     * @return
     */
    private ViewResolver getViewResolver(Ioc ioc) {
        if (viewResolver == null) {
            viewResolver = ioc.getBean(ViewResolver.class);
        }
        if (viewResolver == null) {
            viewResolver = new CombViewResolver();
        }
        return viewResolver;
    }
}
