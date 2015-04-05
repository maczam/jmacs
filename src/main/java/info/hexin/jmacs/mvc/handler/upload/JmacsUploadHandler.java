package info.hexin.jmacs.mvc.handler.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.jmacs.mvc.annotation.UploadType;
import info.hexin.jmacs.mvc.model.UploadParam;
import info.hexin.lang.Exceptions;
import info.hexin.lang.string.Strings;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.fastupload.exception.FileUploadParserException;

/**
 * 文件上传
 *
 * @author hexin
 */
public class JmacsUploadHandler {
    private static final Log logger = Logs.get();

    private AbstractUploadParser uploadParser;

    public JmacsUploadHandler(HttpServletRequest request, UploadParam uploadParam) {
        try {
            String contextType = request.getContentType();// request.getHeader("Content-type");
            if (Strings.isBlank(contextType)) {
                throw Exceptions.make("contextType 为空！！");
            }

            byte[] contextTypeByte = null;
            String[] content = contextType.split(";");
            if (content.length > 1) {
                contextTypeByte = ("--" + content[1].split("=")[1]).getBytes();
            } else {
                throw new FileUploadParserException();
            }

            InputStream inputStream = request.getInputStream();
            int totalSize = request.getContentLength();

            // 内存上传
            if (UploadType.MEMORY == uploadParam.getUploadType()) {
                logger.debug("内存文件上传。。。");
                uploadParser = new MemoryUploadParser(inputStream, totalSize, contextTypeByte, uploadParam);
            }
            // 硬盘上传
            else {
                logger.debug("硬盘文件上传。。。");
                uploadParser = new DiskUploadParser(inputStream, totalSize, contextTypeByte, uploadParam);
            }
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public List<TempFile> parseMultiPartList() {
        return uploadParser.parseMultiPartList();
    }
}
