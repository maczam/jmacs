package info.hexin.jmacs.mvc.interceptor;

import java.io.IOException;

import info.hexin.jmacs.mvc.view.ViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * simplified implementation
 * 
 * @author hexin
 * 
 */
public abstract class HandlerInterceptorAdapter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, ViewModel viewModel)
            throws IOException {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException{

    }
}
