package info.hexin.jmacs.mvc.config;

import info.hexin.jmacs.ioc.IocBean;
import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.ioc.context.impl.SimpleIoc;
import info.hexin.jmacs.ioc.loader.impl.AnnotationLoader;
import info.hexin.jmacs.ioc.loader.impl.XmlLoader;
import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.jmacs.mvc.annotation.Action;
import info.hexin.jmacs.mvc.annotation.HeaderParam;
import info.hexin.jmacs.mvc.annotation.Interceptor;
import info.hexin.jmacs.mvc.annotation.MultiFile;
import info.hexin.jmacs.mvc.annotation.Param;
import info.hexin.jmacs.mvc.annotation.Path;
import info.hexin.jmacs.mvc.annotation.PathParam;
import info.hexin.jmacs.mvc.annotation.Upload;
import info.hexin.jmacs.mvc.annotation.UploadType;
import info.hexin.jmacs.mvc.annotation.View;
import info.hexin.jmacs.mvc.handler.mapping.UrlMaping;
import info.hexin.jmacs.mvc.handler.mapping.UrlPattern;
import info.hexin.jmacs.mvc.interceptor.HandlerInterceptor;
import info.hexin.jmacs.mvc.model.Arg;
import info.hexin.jmacs.mvc.model.InterceptorInfo;
import info.hexin.jmacs.mvc.model.MethodInfo;
import info.hexin.jmacs.mvc.model.UploadParam;
import info.hexin.jmacs.mvc.model.ViewInfo;
import info.hexin.lang.UrlPaths;
import info.hexin.lang.reflect.Klass;
import info.hexin.lang.string.Strings;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;
import javax.servlet.FilterRegistration;

/**
 * 解析web.xml配置文件，生成ioc
 * 
 * @author hexin
 * 
 */
public class WebConfigLoader {
    private static final Log logger = Logs.get();

    private static final String FILE_SPLIT = ";";
    private static final String EXCLUSION = "exclusion";
    private static final String DEFAULT_EXCLUSION = "^.+\\.(jsp|png|gif|jpg|js|css|jspx|jpeg|swf|ico)$";

    // 可以配置的选项
    private static final String PACKAGES = "packages";
    private static final String XMLS = "xmls";

    /**
     * 构造webioc容器，如果
     * 
     * @param filterConfig
     */
    public WebContext createWebContext(FilterConfig filterConfig) {
        WebContext webContext = new WebContext();
        WebConfig webConfig = createWebConfig(filterConfig);
        Ioc ioc = createIoc(webConfig);
        webContext.setIoc(ioc);
        webContext.setWebConfig(webConfig);
        UrlMaping urlMaping = createUrlMaping(ioc, webConfig);
        webContext.setUrlMaping(urlMaping);
        return webContext;
    }

    /**
     * 
     * 创建urlmapping
     * 
     * @param ioc
     * @param webConfig
     * @return
     */
    private UrlMaping createUrlMaping(Ioc ioc, WebConfig webConfig) {

        UrlMaping urlMaping = new UrlMaping();

        Collection<String> collectionMaping = webConfig.getCollectionMaping();
        logger.info("filter maping >>>>> {}", collectionMaping);
        for (String mapping : collectionMaping) {
            logger.debug("filter mapping >>>>> {} ", mapping);

            // 需要将context 存放到缓存中保存起来
            Map<String, IocBean> map = ioc.getIocBeans();
            for (Map.Entry<String, IocBean> entry : map.entrySet()) {
                IocBean iocBean = entry.getValue();
                Class<?> clazz = iocBean.getClazz();
                Interceptor interceptor = clazz.getAnnotation(Interceptor.class);
                Action action = clazz.getAnnotation(Action.class);
                if (interceptor != null && !Strings.isBlank(interceptor.value())) {
                    if (Klass.isInterfaceFrom(clazz, HandlerInterceptor.class)) {
                        String interceptorMapping = UrlPaths.combinePattern(mapping, interceptor.value());
                        InterceptorInfo interceptorInfo = new InterceptorInfo();
                        interceptorInfo.setInterceptorName(iocBean.getName());
                        interceptorInfo.setUrl(interceptorMapping);
                        interceptorInfo.setUrlPattern(UrlPattern.compile(interceptorMapping));
                        interceptorInfo.setClazz(clazz);
                        urlMaping.addInterceptorInfo(interceptorInfo);
                    }
                } else if (action != null) {
                    Path path = clazz.getAnnotation(Path.class);
                    String classPathValue = "";

                    // 类上没有标注注解
                    if (path != null) {
                        classPathValue = path.value();
                    }
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        MethodInfo methodInfo = createMethodInfo(urlMaping, iocBean, classPathValue, method);
                        if (methodInfo != null) {
                            urlMaping.addMethodInfo(methodInfo);
                        }
                    }
                }
            }
        }
        return urlMaping;
    }

    private MethodInfo createMethodInfo(UrlMaping urlMaping, IocBean iocBean, String classPathValue, Method method) {
        Path methodPath = method.getAnnotation(Path.class);
        if (methodPath != null) {
            MethodInfo methodInfo = new MethodInfo();
            methodInfo.setClazz(iocBean.getClazz());
            methodInfo.setReflectMethod(method);
            String methodPathValue = methodPath.value();

            // 已经为classAtValue 增加了 "\"
            if (!methodPathValue.startsWith("/")) {
                methodPathValue = "/" + methodPathValue;
            }
            if (Strings.isNotBlank(classPathValue) && !classPathValue.startsWith("/")) {
                classPathValue = "/" + classPathValue;
            }
            String fullUrl = classPathValue + methodPathValue;

            methodInfo.setUrl(fullUrl);
            methodInfo.setUrlPattern(UrlPattern.compile(fullUrl));
            methodInfo.setMethod(methodPath.method());
            info.hexin.jmacs.mvc.annotation.Method methodMethod = methodPath.method();
            logger.info("methodPathValue >>>> " + methodPathValue + " ;full path >>>> " + methodInfo.getUrl()
                    + " ;method >>> " + methodMethod + "; MethodName : " + method.getName() + "");

            // 方法的参数列表
            methodInfo.setArgs(args(method));
            methodInfo.setUploadParam(uploadParam(method));

            View view = method.getAnnotation(View.class);
            ViewInfo viewInfo = new ViewInfo();
            if (view != null) {
                viewInfo.setPath(view.path());
                viewInfo.setViewType(view.type());
                methodInfo.setViewInfo(viewInfo);
            }
            // add to urlmaping
            return methodInfo;
        }
        return null;
    }

    /**
     * 获取上传属性
     * 
     * @param method
     * @return
     */
    private UploadParam uploadParam(Method method) {
        Upload upload = method.getAnnotation(Upload.class);
        UploadParam uploadParam = new UploadParam();
        if (upload != null) {
            uploadParam.setBufferdSize(upload.bufferdSize());
            uploadParam.setMaxSize(upload.maxSize());
            uploadParam.setForbid(upload.forbid());
            uploadParam.setTmpPath(upload.tmpPath());
            uploadParam.setUploadType(upload.type());
        } else {
            uploadParam.setBufferdSize(1024);
            uploadParam.setMaxSize(Integer.MAX_VALUE);
            uploadParam.setTmpPath("~/upload");
            uploadParam.setUploadType(UploadType.MEMORY);
        }
        return uploadParam;
    }

    /**
     * 获取方法的参数
     * 
     * @param method
     * @return
     */
    private List<Arg> args(Method method) {
        List<Arg> argList = new ArrayList<Arg>();
        Class<?>[] paramClass = method.getParameterTypes();
        Annotation[][] paramAnnotation = method.getParameterAnnotations();
        for (int i = 0; i < paramClass.length; i++) {
            Arg arg = new Arg();
            arg.setParamClass(paramClass[i]);

            // / method param annotation
            Annotation[] annotations = paramAnnotation[i];
            if (annotations.length == 1) {
                // 写在这里 能将部分计算转移到启动时候
                Class<?> anClass = annotations[0].annotationType();
                logger.debug("  anClass >>> " + anClass);// 参数类型
                arg.setAnClass(anClass);
                Annotation annotation = annotations[0];
                if (anClass == HeaderParam.class) {
                    HeaderParam headerParam = (HeaderParam) annotation;
                    arg.setName(headerParam.value());
                } else if (anClass == Param.class) {
                    Param param = (Param) annotation;
                    arg.setName(param.value());
                } else if (anClass == PathParam.class) {
                    try {
                        PathParam pathParam = (PathParam) annotation;
                        String pathParamName = pathParam.value();
                        arg.setName(pathParamName);
                    } catch (Exception e) {
                        throw new RuntimeException("method >>>> " + method.getName()
                                + " @Path is not {} wildcard character!");
                    }
                } else if (anClass == MultiFile.class) {
                    MultiFile multiFile = (MultiFile) annotation;
                    String multiFileName = multiFile.value();
                    arg.setName(multiFileName);
                }
            }
            argList.add(arg);
        }
        return argList;
    }

    /**
     * 根据配置的xml或者package来生成ioc容器
     * 
     * @param webConfig
     * @return
     */
    private Ioc createIoc(WebConfig webConfig) {
        Ioc parent = null;

        // xml
        String xmls = webConfig.getXmls();
        if (Strings.isNotBlank(xmls)) {
            logger.info("loading xmls file >>>>> {}", xmls);
            parent = new SimpleIoc(new XmlLoader(xmls.split(FILE_SPLIT)));
        }

        // 使用包来加载容器
        String packages = webConfig.getPackages();
        if (Strings.isNotBlank(packages)) {
            logger.info("loading packages >>>>> {}", packages);
            Ioc ioc = new SimpleIoc(new AnnotationLoader(packages.split(FILE_SPLIT)));
            if (parent == null) {
                parent = ioc;
            } else {
                parent.load(ioc);
            }
        }
        return parent;
    }

    /**
     * 创建部分配置文件WebConfig
     * 
     * @param filterConfig
     * @return
     */
    private WebConfig createWebConfig(FilterConfig filterConfig) {
        WebConfig webConfig = new WebConfig();

        // filter
        String filterName = filterConfig.getFilterName();
        FilterRegistration registration = filterConfig.getServletContext().getFilterRegistration(filterName);
        Collection<String> collectionMaping = registration.getUrlPatternMappings();
        webConfig.setFilterName(filterName);
        webConfig.setCollectionMaping(collectionMaping);

        // ioc
        // 使用xml配置加载容器
        String xmls = filterConfig.getInitParameter(XMLS);
        webConfig.setXmls(xmls);
        String packages = filterConfig.getInitParameter(PACKAGES);
        webConfig.setPackages(packages);

        // exclusions
        Pattern exclusionPattern = null;
        String exclusions = filterConfig.getInitParameter(EXCLUSION);
        if (exclusions != null) {
            logger.info("exclusion  >>>>> {}", exclusions);
        } else {
            logger.info("default exclusion  >>>>> {}", DEFAULT_EXCLUSION);
            exclusions = DEFAULT_EXCLUSION;
        }
        exclusionPattern = Pattern.compile(exclusions, Pattern.CASE_INSENSITIVE);

        webConfig.setExclusionPattern(exclusionPattern);
        return webConfig;
    }
}
