package info.hexin.json.transform;

import info.hexin.json.transform.impl.ArrayStringTransform;
import info.hexin.json.transform.impl.ArrayTransform;
import info.hexin.json.transform.impl.BooleanTransform;
import info.hexin.json.transform.impl.ByteTransform;
import info.hexin.json.transform.impl.CharTransform;
import info.hexin.json.transform.impl.DateTransform;
import info.hexin.json.transform.impl.DoubleTransform;
import info.hexin.json.transform.impl.FloatTransform;
import info.hexin.json.transform.impl.IntTransform;
import info.hexin.json.transform.impl.ListTransform;
import info.hexin.json.transform.impl.LongTransform;
import info.hexin.json.transform.impl.ShortTransform;
import info.hexin.json.transform.impl.StringTransform;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类型转化
 * 
 * @author hexin
 * 
 */
public class JsonTransformConfig {
    private static Map<Class<?>, JsonTransform<?>> transformInstanceMap = new HashMap<Class<?>, JsonTransform<?>>();
    static {
        transformInstanceMap.put(String.class, StringTransform.instance);
        transformInstanceMap.put(List.class, ListTransform.instance);
        transformInstanceMap.put(int.class, IntTransform.instance);
        transformInstanceMap.put(Integer.class, IntTransform.instance);
        transformInstanceMap.put(long.class, LongTransform.instance);
        transformInstanceMap.put(Long.class, LongTransform.instance);
        transformInstanceMap.put(boolean.class, BooleanTransform.instance);
        transformInstanceMap.put(Boolean.class, BooleanTransform.instance);
        transformInstanceMap.put(byte.class, ByteTransform.instance);
        transformInstanceMap.put(Byte.class, ByteTransform.instance);
        transformInstanceMap.put(char.class, CharTransform.instance);
        transformInstanceMap.put(Character.class, LongTransform.instance);
        transformInstanceMap.put(double.class, DoubleTransform.instance);
        transformInstanceMap.put(Double.class, DoubleTransform.instance);
        transformInstanceMap.put(short.class, ShortTransform.instance);
        transformInstanceMap.put(Short.class, ShortTransform.instance);
        transformInstanceMap.put(float.class, FloatTransform.instance);
        transformInstanceMap.put(Float.class, FloatTransform.instance);

        
        //array
        transformInstanceMap.put(String[].class, ArrayStringTransform.instance);

        transformInstanceMap.put(Date.class, DateTransform.instance);
    }

    public static JsonTransform<?> getTransform(Class<?> clazz) {

        JsonTransform<?> transform = transformInstanceMap.get(clazz);
        // 从父类 或者接口中获取
        if (transform == null) {
            Class<?> supClass = clazz.getSuperclass();
            transform = transformInstanceMap.get(supClass);
        }

        // 从接口中获取,主要针对的map list等
        if (transform == null) {
            Class<?>[] supInterface = clazz.getInterfaces();
            for (Class<?> class1 : supInterface) {
                transform = transformInstanceMap.get(class1);
                if (transform != null) {
                    // 自动学习，下次就不在重复查找
                    transformInstanceMap.put(clazz, transform);
                    break;
                }
            }
        }

        // 对于数组的处理
        if (transform == null && clazz.isArray()) {
            transform = ArrayTransform.instance;
            transformInstanceMap.put(clazz, transform);
        }
        return transform;
    }
}
