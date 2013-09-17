package info.hexin.json.transform.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;

import java.util.List;

public class ArrayStringTransform implements JsonTransform<String[]> {
    public static final ArrayStringTransform instance = new ArrayStringTransform();

    @SuppressWarnings("unchecked")
    @Override
    public String[] transform(Object value, Class<String[]> clazz, JsonConfig config) {
        List<Object> lists = (List<Object>) value;
        String[] resultList = new String[lists.size()];
        for (int i = 0, sum = lists.size(); i < sum; i++) {
            Object object = lists.get(i);
            if (object instanceof String) {
                resultList[i] = (String) object;
            } else {
                resultList[i] = object.toString();
            }
        }
        return resultList;
    }
}
