package info.hexin.jmacs.mvc.model;

import info.hexin.jmacs.mvc.handler.mapping.UrlPattern;

public class InterceptorInfo {

    private String url;
    private UrlPattern urlPattern;
    private String interceptorName;
    private Class<?> clazz;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UrlPattern getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(UrlPattern urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String getInterceptorName() {
        return interceptorName;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
