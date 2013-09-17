package info.hexin.jmacs.mvc.handler.upload;

import info.hexin.jmacs.mvc.annotation.UploadType;
import info.hexin.jmacs.mvc.model.UploadParam;

import java.io.InputStream;
import java.util.List;

/**
 * 文件先写到硬盘上
 * 
 * @author hexin
 * 
 */
public class DiskUploadParser extends AbstractUploadParser {

    public DiskUploadParser(InputStream inputStream, int contentLength, byte[] contextType, UploadParam uploadParam) {
        super(inputStream, contentLength, contextType, uploadParam);
    }

    @Override
    public List<TempFile> parseMultiPartList() {

        // if( (uploadType == UploadType.DISK && !fieldHeadContext.isFile())){
        //
        // }
        return null;
    }
}
