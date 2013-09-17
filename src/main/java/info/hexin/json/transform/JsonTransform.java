package info.hexin.json.transform;

import info.hexin.json.JsonConfig;

/**
 * 转化
 * 
 * @author hexin
 * 
 */
public interface JsonTransform<T> {
    T transform(Object value, Class<T> clazz, JsonConfig config);
}
