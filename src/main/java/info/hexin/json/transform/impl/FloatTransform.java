package info.hexin.json.transform.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;

public class FloatTransform implements JsonTransform<Float> {

    public static final FloatTransform instance = new FloatTransform();

    @Override
    public Float transform(Object value, Class<Float> clazz, JsonConfig config) {
        return Float.valueOf(value.toString());
    }
}