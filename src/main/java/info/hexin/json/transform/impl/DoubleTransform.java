package info.hexin.json.transform.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;

public class DoubleTransform implements JsonTransform<Double> {

    public static final DoubleTransform instance = new DoubleTransform();

    @Override
    public Double transform(Object value, Class<Double> clazz, JsonConfig config) {
        return Double.valueOf(value.toString());
    }
}