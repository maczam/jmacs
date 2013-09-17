package info.hexin.json.transform.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;

public class LongTransform implements JsonTransform<Long> {

    public static final LongTransform instance = new LongTransform();

    @Override
    public Long transform(Object value, Class<Long> clazz, JsonConfig config) {
        return Long.valueOf(value.toString());
    }
}
