package info.hexin.jmacs.mvc.util;

import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.jmacs.mvc.config.WebContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author hexin
 * 
 */
public class SendUtils {
    private static final Log logger = Logs.get();

    /**
     * 出现500错误，时返回定义好的500页面
     * 
     * @param request
     * @param response
     * @param e
     */
    public static void send500Page(WebContext webContext, HttpServletRequest request, HttpServletResponse response,
            Exception e) {
        request.setAttribute("error", e);
        try {
            response.sendError(500, e.toString());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 出现500错误，时返回定义好的500页面
     * 
     * @param request
     * @param response
     * @param e
     */
    public static void send404Page(WebContext webContext, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendError(404, "mapping error!!");
        } catch (Exception e1) {
            logger.error("", e1);
            e1.printStackTrace();
        }
    }
}
