package info.hexin.jmacs.mvc.handler.process;

import info.hexin.jmacs.mvc.config.RequestContext;
import info.hexin.jmacs.mvc.view.ViewModel;

import java.io.IOException;

/**
 * 处理request,不要将东西放在里面，因为不是线程安全
 * 
 * @author ydhexin@gmail.com
 * 
 */
public interface RequestProcess {

    /**
     * 处理请求
     * 
     * @param requestContext
     * @param methodInfo
     * @return
     */
    abstract ViewModel process(RequestContext requestContext) throws IOException;
}
