package info.hexin.json;

import info.hexin.json.parser.DefaultJsonParer;
import info.hexin.json.parser.JSONTokener;
import info.hexin.json.serialize.JsonSerialize;
import info.hexin.json.serialize.StringWrite;
import info.hexin.json.transform.JsonTransform;
import info.hexin.lang.Exceptions;

/**
 * json工具类
 * 
 * @author hexin
 * 
 */
public class Json {

    /**
     * 序列化实体
     * 
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return toJson(obj, new JsonConfig());
    }

    /**
     * 序列化实体
     * 
     * @param obj
     * @return
     */
    public static String toJson(Object obj, JsonConfig config) {
        StringWrite write = new StringWrite();
        Class<?> clazz = obj.getClass();
        JsonSerialize serialize = config.getSerialize(clazz);
        serialize.render(obj, write, config);
        return write.toString();
    }

    /**
     * 反序列话json字符串。返回只有HashMap和ArrayList两种<br>
     * <li>
     * 如果json为"{"开头返回的为HashMap <li>
     * 如果为"["开头，返回的为ArrayList
     * 
     * @param json
     * @return
     */
    public static Object fromJson(String json) {
        return fromJson(json, null, new JsonConfig());
    }

    /**
     * 尝试将json转化成传进来的clazz的实例，
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return fromJson(json, clazz, new JsonConfig());
        } catch (Exception e) {
            throw Exceptions.make(e);
        }
    }

    /**
     * config有这个 就可以修改解析器默认配置
     * 
     * @param json
     * @param clazz
     * @param config
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Class<T> clazz, JsonConfig config) {
        try {
            //防止为简单数据类型，也就是json只是一个简单的String不是以"{""["开头
            if(json.startsWith("[") || json.startsWith("{")){
                DefaultJsonParer jsonParer = new DefaultJsonParer(new JSONTokener(json));
                Object mapList = jsonParer.parser();
                if (clazz == null) {
                    return (T) mapList;
                }
                JsonTransform<T> jsonTransform = (JsonTransform<T>) config.getTransform(clazz);
                return jsonTransform.transform(mapList, clazz, config);
            } else {
                JsonTransform<T> jsonTransform = (JsonTransform<T>) config.getTransform(clazz);
                return (T) jsonTransform.transform(json, clazz, config);
            }
        } catch (Exception e) {
            throw Exceptions.make(e);
        }
    }
}
