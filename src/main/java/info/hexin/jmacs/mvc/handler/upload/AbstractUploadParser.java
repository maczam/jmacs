package info.hexin.jmacs.mvc.handler.upload;

import info.hexin.jmacs.mvc.model.UploadParam;

import java.io.InputStream;
import java.util.List;

/**
 * 文件上传解析类
 * 
 * @author hexin
 * 
 */
public abstract class AbstractUploadParser {

    private InputStream inputStream;

    public AbstractUploadParser(InputStream inputStream, int contentLength, byte[] contextTypeByte,
            UploadParam uploadParam) {
        this.inputStream = inputStream;
    }

    public abstract List<TempFile> parseMultiPartList();
}
