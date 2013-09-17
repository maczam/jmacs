package info.hexin.json.transform.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;

import java.util.List;

public class ArrayTransform implements JsonTransform<Object> {

    public static final JsonTransform<?> instance = new ArrayTransform();

    @SuppressWarnings("unchecked")
    @Override
    public Object transform(Object value, Class<Object> clazz, JsonConfig config) {
        List<Object> lists = (List<Object>) value;
        return lists.toArray();
    }
}
