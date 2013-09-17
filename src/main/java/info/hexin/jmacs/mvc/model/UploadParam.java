package info.hexin.jmacs.mvc.model;

import info.hexin.jmacs.mvc.annotation.UploadType;

/**
 * upload上传参数
 * 
 * @author hexin
 * 
 */
public class UploadParam {

    private int bufferdSize;
    private int maxSize;
    private String forbid;
    private String tmpPath;
    private UploadType uploadType;

    public int getBufferdSize() {
        return bufferdSize;
    }

    public void setBufferdSize(int bufferdSize) {
        this.bufferdSize = bufferdSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String getForbid() {
        return forbid;
    }

    public void setForbid(String forbid) {
        this.forbid = forbid;
    }

    public String getTmpPath() {
        return tmpPath;
    }

    public void setTmpPath(String tmpPath) {
        this.tmpPath = tmpPath;
    }

    public UploadType getUploadType() {
        return uploadType;
    }

    public void setUploadType(UploadType uploadType) {
        this.uploadType = uploadType;
    }
}
