package info.hexin.jmacs.mvc;

import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.jmacs.mvc.config.WebConfigLoader;
import info.hexin.jmacs.mvc.config.WebContext;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 初始化配置配置文件等
 * 
 * @author hexin
 * 
 */
public abstract class AbstractWebFilter implements Filter {
    static Log logger = Logs.get();
    protected WebContext webContext;
    private Pattern pattern;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String url = servletRequest.getRequestURI();
        // 是否托管到框架来处理
        if (pattern.matcher(url).find()) {
            chain.doFilter(request, response);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("RequestURI >>>>> {} ,contentType >>>>> {}", url, request.getContentType());
            }
            dispatcher(servletRequest, servletResponse, chain);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("WebFilter init ....");
        WebConfigLoader configLoader = new WebConfigLoader();
        webContext = configLoader.createWebContext(filterConfig);
        pattern = webContext.getWebConfig().getExclusionPattern();
        initWebBean(webContext, filterConfig);
        logger.info(webContext.getUrlMaping());
    }

    /**
     * 解析配置文件等
     * 
     * @param webContext
     */
    public abstract void initWebBean(WebContext webContext, FilterConfig filterConfig);

    /**
     * 
     * @param servletRequest
     * @param servletResponse
     */
    public abstract void dispatcher(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
            FilterChain chain);
}
