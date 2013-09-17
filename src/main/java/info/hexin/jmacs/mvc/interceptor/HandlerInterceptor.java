package info.hexin.jmacs.mvc.interceptor;

import java.io.IOException;

import info.hexin.jmacs.mvc.view.ViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器接口
 * 
 * @author hexin
 * 
 */
public interface HandlerInterceptor {

    /**
     * 在action处理请求之前
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 在action处理过请求，并且生成ViewModel,在渲染之前
     * 
     * @param request
     * @param response
     * @param viewModel
     * @throws IOException
     */
    void postHandle(HttpServletRequest request, HttpServletResponse response, ViewModel viewModel) throws IOException;

    /**
     * 在渲染之后,如果没有拦截器来接收异常的话。那么异常会导致出现500页面
     * 
     * @param request
     * @param response
     * @param ex
     * @throws IOException
     */
    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException;
}
