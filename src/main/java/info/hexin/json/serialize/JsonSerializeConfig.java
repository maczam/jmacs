package info.hexin.json.serialize;

import info.hexin.json.serialize.impl.ArrayIntSerialize;
import info.hexin.json.serialize.impl.ArraySerialize;
import info.hexin.json.serialize.impl.BooleanSerialize;
import info.hexin.json.serialize.impl.DateSerialize;
import info.hexin.json.serialize.impl.IntegerSerialize;
import info.hexin.json.serialize.impl.ListSerialize;
import info.hexin.json.serialize.impl.MapSerialize;
import info.hexin.json.serialize.impl.ObjectSerialize;
import info.hexin.json.serialize.impl.SetSerialize;
import info.hexin.json.serialize.impl.StringSerialize;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * json序列化全局配置文件，对什么对象如何使用那个类来进行序列话
 * 
 * @author hexin
 * 
 */
public class JsonSerializeConfig {
    private static Map<Class<?>, JsonSerialize> jsonRenderMap = new HashMap<Class<?>, JsonSerialize>();

    static {
        // 可以增加
        jsonRenderMap.put(Map.class, MapSerialize.instance);
        jsonRenderMap.put(String.class, StringSerialize.instance);
        jsonRenderMap.put(Date.class, DateSerialize.instance);
        jsonRenderMap.put(List.class, ListSerialize.instance);
        jsonRenderMap.put(Number.class, IntegerSerialize.instance);
        jsonRenderMap.put(Set.class, SetSerialize.instance);
        jsonRenderMap.put(Boolean.class, BooleanSerialize.instance);
        jsonRenderMap.put(boolean.class, BooleanSerialize.instance);
        
        
        //数组
        jsonRenderMap.put(int[].class, ArrayIntSerialize.instance);
    }

    public static JsonSerialize getSerialize(Class<?> clazz) {
        JsonSerialize jsonRender = jsonRenderMap.get(clazz);
        // 从父类 或者接口中获取
        if (jsonRender == null) {
            Class<?> supClass = clazz.getSuperclass();
            jsonRender = jsonRenderMap.get(supClass);
        }

        // 从接口中获取
        if (jsonRender == null) {
            Class<?>[] supInterface = clazz.getInterfaces();
            for (Class<?> class1 : supInterface) {
                jsonRender = jsonRenderMap.get(class1);
                if (jsonRender != null) {
                    // 自动学习，下次就不在重复查找
                    jsonRenderMap.put(clazz, jsonRender);
                    break;
                }
            }
        }

        ///数组
        if(jsonRender == null && clazz.isArray()){
            jsonRender = ArraySerialize.instance;
            jsonRenderMap.put(clazz, jsonRender);
        }
        
        if (jsonRender == null) {
            jsonRender = ObjectSerialize.instance;
        }
        
//        System.out.println("clazz >>>>> " + clazz +" , jsonRender >>>>>> " + jsonRender);
        return jsonRender;
    }
}
