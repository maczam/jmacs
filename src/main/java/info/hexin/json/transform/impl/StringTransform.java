package info.hexin.json.transform.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;

/**
 * 
 * @author hexin
 * 
 */
public class StringTransform implements JsonTransform<String> {
    public static final StringTransform instance = new StringTransform();

    @Override
    public String transform(Object value, Class<String> clazz, JsonConfig config) {
        return value.toString();
    }
}
