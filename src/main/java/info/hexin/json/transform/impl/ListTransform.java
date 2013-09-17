package info.hexin.json.transform.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;

import java.util.List;

/**
 * 
 * @author hexin
 * 
 */
public class ListTransform implements JsonTransform<List<?>> {

    public static final JsonTransform<?> instance = new ListTransform();

    @Override
    public List<?> transform(Object value, Class<List<?>> clazz, JsonConfig config) {
        return null;
    }
}
