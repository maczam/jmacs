package info.hexin.json.transform.impl;

import info.hexin.json.Json;
import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;
import info.hexin.lang.Exceptions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author hexin
 * 
 */
public class ObjectTransform implements JsonTransform<Object> {
    public static final ObjectTransform instance = new ObjectTransform();

    @SuppressWarnings("unchecked")
    @Override
    public Object transform(Object value, Class<Object> clazz, JsonConfig config) {
        try {
            Object obj = clazz.newInstance();
            Map<String, Object> dataMap = (HashMap<String, Object>) value;
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                fieldAssignment(obj, field, dataMap.get(field.getName()), config);
            }
            return obj;
        } catch (Exception e) {
            throw Exceptions.make(e);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void fieldAssignment(Object instance, Field field, Object value, JsonConfig config) {
        try {
            if (value == null) {
                return;
            }

            Class<?> fieldClass = field.getType();
            if (fieldClass == value.getClass()) {
                setFieldValue1(instance, field, value);
            } else {
                JsonTransform jsonTransform = config.getTransform(fieldClass);
                Object fieldValue = jsonTransform.transform(value, fieldClass, config);
                System.out.println(fieldClass);
                System.out.println(fieldValue.getClass());
                if (fieldClass != fieldValue.getClass()) {
                    if (fieldClass.isArray()) {
                        Object o = Json.fromJson(fieldValue.toString());
                        List<Object> tmpO = (List<Object>) o;
                        setFieldValue1(instance, field, tmpO.toArray(new String[0]));
                    }
                } else {
                    setFieldValue1(instance, field, fieldValue);
                }
            }
        } catch (Exception e) {
            throw Exceptions.make(e);
        }
    }

    /**
     * 修改Field的访问权限，如果为<code>public</code>不用修啊，如果不是修改成<code>public</code>
     * 
     * @param field
     */
    public static String getMethodName(Field field) {
        String methodName = field.getName();
        char[] array = methodName.toCharArray();
        array[0] -= 32;
        return "set" + String.valueOf(array);
    }

    public static void setFieldValue1(Object instance, Field field, Object value) {
        String methodName = getMethodName(field);
        Class<?> fieldClass = field.getType();
        Class<?> klass = instance.getClass();
        try {
            Method m = klass.getMethod(methodName, fieldClass);
            m.invoke(instance, value);
        } catch (NoSuchMethodException e) {
            throw Exceptions.make(String.format("请检查 %s 类是否有 %s 方法", klass.getName(), methodName));
        } catch (IllegalArgumentException e) {
            throw Exceptions.make(String.format("请检查 %s 类 %s 方法参数是否和属性类型一致 ", klass.getName(), methodName));
        } catch (Exception e) {
            throw Exceptions.make(String.format("反射调用 %s 类 %s 方法 出现异常 ", klass.getName(), methodName), e);
        }
    }
}
