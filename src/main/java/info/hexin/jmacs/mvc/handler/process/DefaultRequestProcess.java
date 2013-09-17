package info.hexin.jmacs.mvc.handler.process;

import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.jmacs.mvc.annotation.HeaderParam;
import info.hexin.jmacs.mvc.annotation.Model;
import info.hexin.jmacs.mvc.annotation.MultiFile;
import info.hexin.jmacs.mvc.annotation.Param;
import info.hexin.jmacs.mvc.annotation.PathParam;
import info.hexin.jmacs.mvc.model.Arg;
import info.hexin.jmacs.mvc.model.MethodInfo;
import info.hexin.lang.Exceptions;
import info.hexin.lang.reflect.Reflects;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 文件上传验证
 * 
 * @author ydhexin@gmail.com
 * 
 */
public class DefaultRequestProcess extends AbstractRequestProcess {
    private static final Log logger = Logs.get();
    public static DefaultRequestProcess instance = new DefaultRequestProcess();

    public List<Object> doMethodParam(HttpServletRequest request, HttpServletResponse response, MethodInfo methodInfo,
            Map<String, String> urlPathParam) {
        logger.debug("in method doMethodParam urlPathParam >>>> {}", urlPathParam);
        List<Arg> args = methodInfo.getArgs();
        List<Object> argList = new ArrayList<Object>();
        for (Arg arg : args) {
            Class<?> argAnClazz = arg.getAnClass();
            if (argAnClazz == null) {
                Class<?> paramClass = arg.getParamClass();
                if (paramClass == HttpServletRequest.class) {
                    argList.add(request);
                } else if (paramClass == HttpSession.class) {
                    argList.add(request.getSession());
                } else if (paramClass == HttpServletResponse.class) {
                    argList.add(response);
                }
            } else {
                String paramName = arg.getName();
                if (argAnClazz == HeaderParam.class) {
                    String value = request.getHeader(paramName);
                    argList.add(value);
                } else if (argAnClazz == Param.class) {
                    String value = request.getParameter(paramName);
                    argList.add(value);
                } else if (argAnClazz == PathParam.class) {
                    String pathValue = urlPathParam.get(paramName);
                    argList.add(pathValue);
                } else if (argAnClazz == MultiFile.class) {
                    throw Exceptions.make("确定页面是文件上传。。");
                } else if (argAnClazz == Model.class) {
                    Class<?> paramClass = arg.getParamClass();
                    try {
                        Object argInstance = paramClass.newInstance();
                        Field[] fields = paramClass.getDeclaredFields();
                        for (Field field : fields) {
                            String fieldName = field.getName();
                            String value = request.getParameter(fieldName);
                            Reflects.setFieldValue(argInstance, field, value);
                        }
                        argList.add(argInstance);
                    } catch (Exception e) {
                        throw Exceptions.make(e);
                    }
                }
            }
        }
        return argList;
    }
}
