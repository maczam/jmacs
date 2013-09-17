package info.hexin.json.transform.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;

public class ShortTransform implements JsonTransform<Short> {

    public static final ShortTransform instance = new ShortTransform();

    @Override
    public Short transform(Object value, Class<Short> clazz, JsonConfig config) {
        return Short.valueOf(value.toString());
    }
}
