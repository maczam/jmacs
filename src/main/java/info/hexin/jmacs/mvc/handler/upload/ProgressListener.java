package info.hexin.jmacs.mvc.handler.upload;

/**
 * 进度监听
 * 
 * @author hexin
 * 
 */
public interface ProgressListener {
    public void update(String fieldName, long bytesRead, long contentLength);
}
