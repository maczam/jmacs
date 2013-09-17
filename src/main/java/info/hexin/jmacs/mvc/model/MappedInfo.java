package info.hexin.jmacs.mvc.model;

import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;

/**
 * 接受url的响应，包含Action 和 Interceptor
 * 
 * @author hexin
 * 
 */
public class MappedInfo {

    private MethodInfo methodInfo;
    private Set<InterceptorInfo> interceptorInfos;
    private Map<String, String> urlParamMap;
    private FilterChain chain;

    public MethodInfo getMethodInfo() {
        return methodInfo;
    }

    public void setMethodInfo(MethodInfo methodInfo) {
        this.methodInfo = methodInfo;
    }

    public Set<InterceptorInfo> getInterceptorInfos() {
        return interceptorInfos;
    }

    public void setInterceptorInfos(Set<InterceptorInfo> interceptorInfos) {
        this.interceptorInfos = interceptorInfos;
    }

    public Map<String, String> getUrlParamMap() {
        return urlParamMap;
    }

    public void setUrlParamMap(Map<String, String> urlParamMap) {
        this.urlParamMap = urlParamMap;
    }

    public FilterChain getChain() {
        return chain;
    }

    public void setChain(FilterChain chain) {
        this.chain = chain;
    }
}
