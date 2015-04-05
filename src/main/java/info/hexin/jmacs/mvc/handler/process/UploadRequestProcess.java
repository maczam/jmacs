package info.hexin.jmacs.mvc.handler.process;

import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.jmacs.mvc.annotation.HeaderParam;
import info.hexin.jmacs.mvc.annotation.MultiFile;
import info.hexin.jmacs.mvc.annotation.Param;
import info.hexin.jmacs.mvc.annotation.PathParam;
import info.hexin.jmacs.mvc.annotation.UploadType;
import info.hexin.jmacs.mvc.handler.upload.AbstractUploadParser;
import info.hexin.jmacs.mvc.handler.upload.DiskUploadParser;
import info.hexin.jmacs.mvc.handler.upload.JmacsUploadHandler;
import info.hexin.jmacs.mvc.handler.upload.MemoryUploadParser;
import info.hexin.jmacs.mvc.handler.upload.ProgressListener;
import info.hexin.jmacs.mvc.handler.upload.TempFile;
import info.hexin.jmacs.mvc.model.Arg;
import info.hexin.jmacs.mvc.model.MethodInfo;
import info.hexin.jmacs.mvc.model.UploadParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 文件上传。 需要自己的核心
 * 
 * @author hexin
 * 
 */
public class UploadRequestProcess extends AbstractRequestProcess {
    private final static Log logger = Logs.get();
    public final static UploadRequestProcess instance = new UploadRequestProcess();

    @Override
    public List<Object> doMethodParam(HttpServletRequest request, HttpServletResponse response, MethodInfo methodInfo,
            Map<String, String> urlParamMap) {
        List<Arg> args = methodInfo.getArgs();
        UploadParam uploadParam = methodInfo.getUploadParam();

//        byte[] stream = new byte[request.getContentLength()];
//        byte[] b = new byte[8192];
//        int pos = 0;
//        for (int c = 0; c != -1; c = request.getInputStream().read(b)) {
//            System.arraycopy(b, 0, stream, pos, c);
//            pos += c;
//        }
//        System.out.println("xxxxxxxxxxx");
//        System.out.println(new String(stream));
//        System.out.println("xxxxxxxxxxx");

        String xx = request.getHeader("Content-type");
        System.out.println("xx >>>" + xx);

        //自己默认实现的文件上传类
//        JmacsUploadHandler jmacsUploadHandler = new JmacsUploadHandler(request, uploadParam);
        JmacsUploadHandler jmacsUploadHandler = new JmacsUploadHandler(request, uploadParam);
        List<TempFile> multiPartList = jmacsUploadHandler.parseMultiPartList();

        String requestUrl = request.getRequestURI();
        String tempRequestUrl = requestUrl.substring(1);
        String[] urls = tempRequestUrl.split("/");
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

                    // 需要调用解析后文件
                    for (TempFile tempFile : multiPartList) {
                        if (!tempFile.isFile() && paramName.equals(tempFile.getFieldName())) {
                            argList.add(tempFile.toString());
                            logger.debug("paramName >>>> {}, value >>>> {}",paramName,tempFile.toString());
                            break;
                        }
                    }
                } else if (argAnClazz == PathParam.class) {
                    String pathValue = urls[0];
                    argList.add(pathValue);
                } else if (argAnClazz == MultiFile.class) {
                    // 需要调用解析后文件
                    for (TempFile tempFile : multiPartList) {
                        if (tempFile.isFile() && paramName.equals(tempFile.getFieldName())) {
                            argList.add(tempFile);
                            logger.debug("paramName >>>> {}, value >>>> {}",paramName,tempFile);
                            break;
                        }
                    }
                }
            }
        }
        logger.debug(argList);
        return argList;
    }
}
