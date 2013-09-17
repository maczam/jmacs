package info.hexin.jmacs.mvc.handler.upload;

/**
 * 
 * 
 * @author hexin
 * 
 */
public abstract class TempFile {

    private boolean isFile;

    private String fieldName;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public abstract String getString();

    public abstract String getString(String charset);

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean isFile) {
        this.isFile = isFile;
    }
}
