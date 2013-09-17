package info.hexin.jmacs.mvc;

import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.jmacs.mvc.config.RequestContext;
import info.hexin.jmacs.mvc.config.WebContext;
import info.hexin.jmacs.mvc.handler.MappedHandler;
import info.hexin.jmacs.mvc.handler.mapping.UrlMaping;
import info.hexin.jmacs.mvc.model.MappedInfo;
import info.hexin.jmacs.mvc.util.SendUtils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 框架的入口
 * 
 * @author hexin
 * 
 */
public class WebFilter extends AbstractWebFilter {

    static Log logger = Logs.get();

    @Override
    public void initWebBean(WebContext webContext, FilterConfig filterConfig) {

    }

    /**
     * 1. 分发需要包含至少包含一个拦截器 或者存在Handler 2. 如果只有拦截器的没有handler的。执行完拦截器
     */
    @Override
    public void dispatcher(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        UrlMaping urlMaping = webContext.getUrlMaping();
        MappedInfo mappedInfo = urlMaping.getMappedHandler(request);
        mappedInfo.setChain(chain);

        RequestContext requestContext = new RequestContext();
        requestContext.setRequest(request);
        requestContext.setResponse(response);
        requestContext.setMappedInfo(mappedInfo);
        requestContext.setIoc(webContext.getIoc());

        if (mappedInfo.getMethodInfo() != null || mappedInfo.getInterceptorInfos().size() > 0) {
            try {
                new MappedHandler().handler(requestContext);
            } catch (IOException e) {
                SendUtils.send500Page(webContext, request, response, e);
            }
        } else {
            SendUtils.send404Page(webContext, request, response);
            return;
        }
    }
}
