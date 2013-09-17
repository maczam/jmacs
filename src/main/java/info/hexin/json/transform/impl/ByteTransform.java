package info.hexin.json.transform.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;

public class ByteTransform implements JsonTransform<Byte> {

    public static final ByteTransform instance = new ByteTransform();

    @Override
    public Byte transform(Object value, Class<Byte> clazz, JsonConfig config) {
        return Byte.valueOf(value.toString());
    }
}
