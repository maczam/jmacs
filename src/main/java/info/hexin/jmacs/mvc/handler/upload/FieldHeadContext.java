package info.hexin.jmacs.mvc.handler.upload;

import java.util.HashMap;

/**
 * 上传域中的 每个input描述
 * 
 * @author hexin
 * 
 */
public class FieldHeadContext {

    private static final String FILE_NAME_KEY = "filename";
    private static final String NAME_KEY = "name";

    HashMap<String, String> describeMap = new HashMap<String, String>();

    public void setDescribeMap(HashMap<String, String> describeMap) {
        this.describeMap.putAll(describeMap);
    }

    public boolean isFile() {
        return this.describeMap.containsKey(FILE_NAME_KEY);
    }

    public String getName() {
        return this.describeMap.get(NAME_KEY);
    }

    public String getFileName() {
        String fn = this.describeMap.get(FILE_NAME_KEY);
        if (fn != null) {
            int i = fn.lastIndexOf("\\");
            if (i != -1)
                fn = fn.substring(i + 1);
        }
        return fn;
    }
}
