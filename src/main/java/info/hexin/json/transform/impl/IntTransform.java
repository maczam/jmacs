package info.hexin.json.transform.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;

public class IntTransform implements JsonTransform<Integer> {

    public static final IntTransform instance = new IntTransform();

    @Override
    public Integer transform(Object value, Class<Integer> clazz, JsonConfig config) {
        return Integer.valueOf(value.toString());
    }
}
