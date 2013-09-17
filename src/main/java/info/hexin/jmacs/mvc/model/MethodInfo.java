package info.hexin.jmacs.mvc.model;

import info.hexin.jmacs.mvc.annotation.Method;
import info.hexin.jmacs.mvc.handler.mapping.UrlPattern;

import java.util.List;

/**
 * 将每次request和一个method相对应
 * 
 * @author ydhexin@gmail.com
 * 
 */
public class MethodInfo {

    private String url;
    private UrlPattern urlPattern;
    private Method method;
    private ViewInfo viewInfo;
    private java.lang.reflect.Method reflectMethod;
    private UploadParam uploadParam;
    private List<Arg> args;
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

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ViewInfo getViewInfo() {
        return viewInfo;
    }

    public void setViewInfo(ViewInfo viewInfo) {
        this.viewInfo = viewInfo;
    }

    public java.lang.reflect.Method getReflectMethod() {
        return reflectMethod;
    }

    public void setReflectMethod(java.lang.reflect.Method reflectMethod) {
        this.reflectMethod = reflectMethod;
    }

    public UploadParam getUploadParam() {
        return uploadParam;
    }

    public void setUploadParam(UploadParam uploadParam) {
        this.uploadParam = uploadParam;
    }

    public List<Arg> getArgs() {
        return args;
    }

    public void setArgs(List<Arg> args) {
        this.args = args;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
