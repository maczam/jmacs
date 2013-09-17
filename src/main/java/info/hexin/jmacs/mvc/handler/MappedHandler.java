package info.hexin.jmacs.mvc.handler;

import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.jmacs.mvc.config.RequestContext;
import info.hexin.jmacs.mvc.handler.process.AbstractRequestProcess;
import info.hexin.jmacs.mvc.handler.process.DefaultRequestProcess;
import info.hexin.jmacs.mvc.handler.process.UploadRequestProcess;
import info.hexin.jmacs.mvc.interceptor.HandlerInterceptor;
import info.hexin.jmacs.mvc.model.InterceptorInfo;
import info.hexin.jmacs.mvc.model.MappedInfo;
import info.hexin.jmacs.mvc.model.MethodInfo;
import info.hexin.jmacs.mvc.view.ViewModel;
import info.hexin.lang.Exceptions;
import info.hexin.lang.http.EncType;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理请求
 * 
 * @author hexin
 * 
 */
public class MappedHandler {
    static Log logger = Logs.get();

    public void handler(RequestContext requestContext) throws IOException {
        Set<HandlerInterceptor> handlerInterceptors = null;
        Ioc ioc = requestContext.getIoc();
        MappedInfo mappedInfo = requestContext.getMappedInfo();
        Set<InterceptorInfo> interceptorInfos = mappedInfo.getInterceptorInfos();
        if (interceptorInfos != null) {
            handlerInterceptors = new HashSet<HandlerInterceptor>();
            for (InterceptorInfo interceptorInfo : interceptorInfos) {
                HandlerInterceptor interceptor = (HandlerInterceptor) ioc.getBean(interceptorInfo.getInterceptorName());
                handlerInterceptors.add(interceptor);
            }
        }

        MethodInfo methodInfo = mappedInfo.getMethodInfo();
        FilterChain chain = mappedInfo.getChain();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        boolean isBreak = false;
        Exception ex = null;
        try {
            // preHandle
            if (handlerInterceptors != null) {
                for (HandlerInterceptor interceptor : handlerInterceptors) {
                    if (!interceptor.preHandle(request, response)) {
                        isBreak = true;
                        break;
                    }
                }
            }

            // postHandle 可能没有配置action
            if (!isBreak) {
                if (methodInfo == null) {
                    chain.doFilter(request, response);
                } else {
                    ViewModel viewModel = actionHandler(requestContext);
                    if (handlerInterceptors != null) {
                        for (HandlerInterceptor handlerInterceptor : handlerInterceptors) {
                            handlerInterceptor.postHandle(request, response, viewModel);
                        }
                    }
                    viewModel.render(ioc, request, response);
                }
            } else {
                logger.info("没有跳过拦截器");
            }
        } catch (Exception e) {
            logger.error("", e);
            ex = e;
        }

        // afterCompletion
        if (handlerInterceptors != null) {
            for (HandlerInterceptor handlerInterceptor : handlerInterceptors) {
                handlerInterceptor.afterCompletion(request, response, ex);
            }
        } else {
            throw Exceptions.make(ex);
        }
    }

    /**
     * 
     * @param requestContext
     * @return
     */
    private ViewModel actionHandler(RequestContext requestContext) {
        String contentType = requestContext.getRequest().getContentType();
        try {
            AbstractRequestProcess requestProcess = getRequestProcess(contentType);
            return requestProcess.process(requestContext);
        } catch (Exception e) {
            throw Exceptions.make(e);
        }
    }

    /**
     * 
     * @param contentType
     * @return
     */
    private AbstractRequestProcess getRequestProcess(String contentType) {
        if (contentType == null || contentType.contains(EncType.URLENCODED)) {
            return DefaultRequestProcess.instance;
        } else if (contentType.contains(EncType.FORMDATA) || contentType.contains(EncType.OCTETSTREAM)) {
            // return FastuploadRequestProcess.instance
            return UploadRequestProcess.instance;
        } else {
            throw Exceptions.make("文件上传缺少 multipart/form-data");
        }
    }
}
