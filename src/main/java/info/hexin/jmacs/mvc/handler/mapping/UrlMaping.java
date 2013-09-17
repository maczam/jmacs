package info.hexin.jmacs.mvc.handler.mapping;

import info.hexin.jmacs.mvc.annotation.Method;
import info.hexin.jmacs.mvc.model.InterceptorInfo;
import info.hexin.jmacs.mvc.model.MappedInfo;
import info.hexin.jmacs.mvc.model.MethodInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 将配置的action 对应起来
 * 
 * @author ydhexin@gmail.com
 * 
 */
public class UrlMaping {

    private Map<String, InterceptorInfo> interceptorMap = new HashMap<String, InterceptorInfo>();
    private Map<String, MethodInfo> methodMap = new HashMap<String, MethodInfo>();

    public void addMethodInfo(MethodInfo methodInfo) {
        methodMap.put(methodInfo.getUrl(), methodInfo);
    }

    /**
     * 增加拦截器
     * 
     * @param interceptorInfo
     */
    public void addInterceptorInfo(InterceptorInfo interceptorInfo) {
        interceptorMap.put(interceptorInfo.getUrl(), interceptorInfo);
    }

    /**
     * 获取request中的请求
     * 
     * @param request
     * @return
     */
    public MappedInfo getMappedHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        // default handler
        if ("/".equals(requestURI)) {
            requestURI = "/*";
        }
        Method method = Method.typeOf(request.getMethod());
        MappedInfo mappedInfo = new MappedInfo();

        // methodinfo
        MethodInfo methodInfo = methodMap.get(requestURI);
        if (methodInfo == null) {
            for (Entry<String, MethodInfo> entry : methodMap.entrySet()) {
                UrlMatcher matcher = entry.getValue().getUrlPattern().mather(requestURI);
                methodInfo = entry.getValue();
                if (matcher.matched() && methodInfo.getMethod().equal(method)) {
                    mappedInfo.setMethodInfo(methodInfo);
                    mappedInfo.setUrlParamMap(matcher.getMatchedMap());
                    break;
                }
            }
        } else {
            mappedInfo.setMethodInfo(methodInfo);
        }

        // InterceptorInfo
        Set<InterceptorInfo> interceptorInfos = new HashSet<InterceptorInfo>();
        InterceptorInfo interceptorInfo = interceptorMap.get(requestURI);
        if (interceptorInfo == null) {
            for (Entry<String, InterceptorInfo> entry : interceptorMap.entrySet()) {
                UrlMatcher matcher = entry.getValue().getUrlPattern().mather(requestURI);
                if (matcher.matched()) {
                    interceptorInfo = entry.getValue();
                    interceptorInfos.add(interceptorInfo);
                }
            }
        } else {
            interceptorInfos.add(interceptorInfo);
        }
        mappedInfo.setInterceptorInfos(interceptorInfos);
        return mappedInfo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n /*************************************/ \n");
        sb.append("interceptor>>>> \n");
        for (Entry<String, InterceptorInfo> entry : interceptorMap.entrySet()) {
            InterceptorInfo interceptorInfo = entry.getValue();
            sb.append("[ url >> ").append(interceptorInfo.getUrl());
            sb.append(" , class >> ").append(interceptorInfo.getClazz());
            sb.append("]").append("\n");
        }
        sb.append("/*************************************/ \n");
        sb.append("action>>>> \n");
        for (Entry<String, MethodInfo> entry : methodMap.entrySet()) {
            MethodInfo methodInfo = entry.getValue();
            sb.append("[ url >> ").append(methodInfo.getUrl());
            sb.append(" , method >> ").append(methodInfo.getMethod());
            sb.append(" , class >> ").append(methodInfo.getClazz());
            sb.append("]").append("\n");
        }
        return sb.toString();
    }
}
