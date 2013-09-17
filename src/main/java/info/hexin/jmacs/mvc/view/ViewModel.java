package info.hexin.jmacs.mvc.view;

import info.hexin.jmacs.ioc.context.Ioc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 视图接口，具体实现jsonview。。。
 * 
 * @author hexin
 * 
 */
public interface ViewModel {

    /**
     * 如果自己碰了response，需要自己去管理
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    void render(Ioc ioc, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
