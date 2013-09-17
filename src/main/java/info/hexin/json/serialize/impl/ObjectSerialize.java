package info.hexin.json.serialize.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.serialize.JsonSerialize;
import info.hexin.json.serialize.StringWrite;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author hexin
 * 
 */
public class ObjectSerialize implements JsonSerialize {
    public static ObjectSerialize instance = new ObjectSerialize();

    @Override
    public void render(Object object, StringWrite write,JsonConfig jsonConfig) {
        try {
            write.append('{');
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();

            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    if (value != null) {
                        map.put(field.getName(), value);
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            int i = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                write.appendFiledNameWithQuotation(entry.getKey());
                write.append(':');
                JsonSerialize render = jsonConfig.getSerialize(entry.getValue().getClass());
                render.render(entry.getValue(), write,jsonConfig);
                if (++i < map.size()) {
                    write.append(',');
                }
            }
            write.append('}');
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
