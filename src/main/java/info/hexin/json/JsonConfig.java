package info.hexin.json;

import info.hexin.json.serialize.JsonSerialize;
import info.hexin.json.serialize.JsonSerializeConfig;
import info.hexin.json.serialize.impl.ObjectSerialize;
import info.hexin.json.transform.JsonTransform;
import info.hexin.json.transform.JsonTransformConfig;
import info.hexin.json.transform.impl.ObjectTransform;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * json 解析全局配置
 * 
 * @author hexin
 * 
 */
public class JsonConfig {

    // 用户自定义的解析器
    private Map<Class<?>, JsonSerialize> serializeMap = new HashMap<Class<?>, JsonSerialize>();
    private Map<Class<?>, JsonTransform<?>> transformMap = new HashMap<Class<?>, JsonTransform<?>>();

    public final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    // ////
    public void registerSerialize(Class<?> clazz, JsonSerialize serialize) {
        serializeMap.put(clazz, serialize);
    }

    public JsonSerialize getSerialize(Class<?> clazz) {
        // 按照类型匹配
        JsonSerialize jsonSerialize = serializeMap.get(clazz);
        if (jsonSerialize == null) {
            jsonSerialize = JsonSerializeConfig.getSerialize(clazz);
        }

        // 使用默认
        if (jsonSerialize == null) {
            jsonSerialize = serializeMap.get(Object.class);
            if (jsonSerialize == null) {
                jsonSerialize = ObjectSerialize.instance;
            }
        }

        // logger.info("clazz >>> {} ................... jsonSerialize >>> {}",
        // clazz, jsonSerialize);
        return jsonSerialize;
    }

    // ///
    public void registerTransform(Class<?> clazz, JsonTransform<?> jsonTransform) {
        transformMap.put(clazz, jsonTransform);
    }

    public JsonTransform<?> getTransform(Class<?> clazz) {
        // 按照类型匹配
        JsonTransform<?> jsonTransform = transformMap.get(clazz);
        if (jsonTransform == null) {
            jsonTransform = JsonTransformConfig.getTransform(clazz);
        }

        // 使用默认
        if (jsonTransform == null) {
            jsonTransform = transformMap.get(Object.class);
            if (jsonTransform == null) {
                jsonTransform = ObjectTransform.instance;
            }
        }
        return jsonTransform;
    }
}
