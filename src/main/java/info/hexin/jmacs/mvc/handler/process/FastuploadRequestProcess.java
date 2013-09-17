package info.hexin.jmacs.mvc.handler.process;

import info.hexin.jmacs.mvc.annotation.HeaderParam;
import info.hexin.jmacs.mvc.annotation.MultiFile;
import info.hexin.jmacs.mvc.annotation.Param;
import info.hexin.jmacs.mvc.annotation.PathParam;
import info.hexin.jmacs.mvc.model.Arg;
import info.hexin.jmacs.mvc.model.MethodInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sourceforge.fastupload.FastUploadParser;
import net.sourceforge.fastupload.MultiPart;

/**
 * 调用fastupload实现文件上传
 * 
 * @author hexin
 * 
 */
public class FastuploadRequestProcess extends AbstractRequestProcess {

    public static final FastuploadRequestProcess instance = new FastuploadRequestProcess();;

    @Override
    public List<Object> doMethodParam(HttpServletRequest request, HttpServletResponse response, MethodInfo methodInfo,
            Map<String, String> urlPathParam) throws IOException {
        List<Arg> args = methodInfo.getArgs();
        List<Object> argList = new ArrayList<Object>();
        byte[] stream = new byte[request.getContentLength()];
        byte[] b = new byte[8192];
        int pos = 0;
        for (int c = 0; c != -1; c = request.getInputStream().read(b)) {
            System.arraycopy(b, 0, stream, pos, c);
            pos += c;
        }

        System.out.println("xxxxxxxxxxx");
        System.out.println(new String(stream));
        System.out.println("xxxxxxxxxxx");

        FastUploadParser fastUploadParser = new FastUploadParser(request);
        List<MultiPart> multiPartList = fastUploadParser.parseList();

        // 开始封装参数
        String requestUrl = request.getRequestURI();
        String tempRequestUrl = requestUrl.substring(1);
        String[] urls = tempRequestUrl.split("/");
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
                    for (MultiPart multiPart : multiPartList) {
                        if (!multiPart.isFile() && paramName.equals(multiPart.getFieldName())) {
                            argList.add(multiPart.getString("utf-8"));
                            break;
                        }
                    }
                } else if (argAnClazz == PathParam.class) {
                    String pathValue = urls[0];
                    argList.add(pathValue);
                } else if (argAnClazz == MultiFile.class) {
                    // 需要调用解析后文件
//                    for (MultiPart multiPart : multiPartList) {
//                        if (multiPart.isFile() && paramName.equals(multiPart.getFieldName())) {
//                            TempFile tempFile = new TempFile();
//                            tempFile.setFileName(multiPart.getFileName());
//                            tempFile.setFileByte(multiPart.getContentBuffer());
//                            argList.add(tempFile);
//                            break;
//                        }
//                    }
                }
            }
        }
        return argList;
    }
}
