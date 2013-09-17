package info.hexin.jmacs.mvc.handler.upload;

import info.hexin.jmacs.mvc.model.UploadParam;
import info.hexin.lang.string.BM;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author hexin
 * 
 */
public class MemoryUploadParser extends AbstractUploadParser {
    private List<TempFile> multiparts = new ArrayList<TempFile>();

    private BMChunk bmChunk;

    public MemoryUploadParser(InputStream inputStream, int contentLength, byte[] contextType, UploadParam uploadParam)
            throws IOException {
        super(inputStream, contentLength, contextType, uploadParam);

        int readBytes = 0;
        byte[] stream = new byte[contentLength];

        int bufferSize = uploadParam.getBufferdSize();
        bmChunk = new BMChunk(new BM(contextType));
        byte[] buff = new byte[bufferSize];
        int c = 0;

        while ((c = inputStream.read(buff)) != -1) {
            if (readBytes == 0) {
                bmChunk.setBuffer(buff);
            } else {
                bmChunk.append(buff, 0, c);
            }
            readBytes += c;
            //进度在这里 contentLength readBytes

            while (bmChunk.find()) {
                FieldHeadContext fieldHeadContext = bmChunk.readContentHeader();
                MemTempFile tempFile = new MemTempFile();
                tempFile.setFieldName(fieldHeadContext.getName());
                int s = bmChunk.getContentStart();
                int len = bmChunk.getBoundEnd() - s - 2;
                if (len > 0) {
                    tempFile.append(bmChunk.getBuffer(), s, len);
                }

                if (fieldHeadContext.isFile()) {
                    tempFile.setFileName(fieldHeadContext.getFileName());
                    tempFile.setFile(true);
                } else {
                    tempFile.setFile(false);
                }
                multiparts.add(tempFile);
            }
        }
        System.out.println("stream >>>> \n" + new String(stream));
        System.out.println("contextType >>>> \n" + new String(contextType));
    }

    @Override
    public List<TempFile> parseMultiPartList() {

        return multiparts;
    }
}
