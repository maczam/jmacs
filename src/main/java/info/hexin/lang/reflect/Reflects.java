package info.hexin.lang.reflect;

import info.hexin.jmacs.ioc.IocBean;
import info.hexin.jmacs.ioc.Property;
import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.json.Json;
import info.hexin.lang.Exceptions;
import info.hexin.lang.Lang;
import info.hexin.lang.string.Strings;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 反射包装类
 * 
 * @author hexin
 * 
 */
public abstract class Reflects {
    static Log log = Logs.get();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * 修改Field的访问权限，如果为<code>public</code>不用修啊，如果不是修改成<code>public</code>
     * 
     * @param field
     */
    public static void setFieldPublicAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 将Map 和List 转化成Object
     * 
     * @param obj
     * @param t
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @SuppressWarnings("unchecked")
    public static <T> T maplist2Object(Object listMapObject, Class<T> clazz) {
        try {
            if (clazz.isInterface()) {
                log.error("clazz  :" + clazz.getName() + "  is Interface,");
                return null;
            }
            T instance = clazz.newInstance();
            if (listMapObject.getClass() == ArrayList.class) {
                // List<Object> list = (List<Object>) listMapObject;
            } else {
                Map<String, Object> map = (HashMap<String, Object>) listMapObject;
                Field[] fields = instance.getClass().getDeclaredFields();
                for (Field field : fields) {
                    setFieldValue(instance, field, map.get(field.getName()));
                }
            }
            return instance;
        } catch (InstantiationException e) {
            throw Exceptions.make(" java bean class is interface or abstra , is not newInstance !!");
        } catch (IllegalAccessException e) {
            throw Exceptions.make(" java bean class is not public  , is not newInstance !!");
        }

    }

    /**
     * 给fied赋值
     * 
     * @param instance
     * @param field
     * @param value
     */
    @SuppressWarnings("unchecked")
    public static void setFieldValue(Object instance, Field field, Object value) {
        try {
            if (value == null) {
                return;
            }
            setFieldPublicAccessible(field);
            Class<?> fieldClass = field.getType();
            log.info("instance class >>>> " + instance.getClass().getName() + ",fieldName >>> " + field.getName()
                    + ", fieldClass >>> " + fieldClass + ", value >>> " + value);
            if (fieldClass != value.getClass()) {
                if (String.class == fieldClass) {
                    field.set(instance, value.toString());
                } else if (fieldClass == int.class || Integer.class == fieldClass) {
                    field.set(instance, Lang.toInt(value));
                } else if (fieldClass == long.class || Long.class == fieldClass) {
                    field.set(instance, Lang.toLong(value));
                } else if (fieldClass == boolean.class || fieldClass == Boolean.class) {
                    field.set(instance, Lang.toBoolean(value));
                } else if (fieldClass == char.class || fieldClass == Character.class) {
                    field.set(instance, Lang.toChar(value));
                } else if (fieldClass == double.class || fieldClass == Double.class) {
                    field.set(instance, Lang.toDouble(value));
                } else if (fieldClass == float.class || fieldClass == Float.class) {
                    field.set(instance, Lang.toFloat(value));
                } else if (fieldClass == byte.class || fieldClass == Byte.class) {
                    field.set(instance, Lang.toByte(value));
                } else if (fieldClass == short.class || fieldClass == Short.class) {
                    field.set(instance, Lang.toShort(value));
                } else if (fieldClass.isArray()) {
                    Object o = Json.fromJson(value.toString());
                    List<Object> tmpO = (List<Object>) o;
                    field.set(instance, tmpO.toArray(new String[0]));
                } else if (Date.class == fieldClass) {
                    value = sdf.parse(value.toString());
                    field.set(instance, value);
                } else {
                    Object fieldInstance = maplist2Object(value, fieldClass);
                    field.set(instance, fieldInstance);
                }
            } else {
                field.set(instance, value);
            }
        } catch (Exception e) {
            throw Exceptions.make(String.format("反射赋值出现异常 类: %s , 属性: %s , 值: %s ", instance.getClass(), field, value),
                    e);
        }
    }

    /**
     * 通过反射给市里的属性赋值,默认先找到set方法，如果set方法不存在，那么就直接反射赋值
     * 
     * @param instance
     * @param iocBean
     * @param ioc
     * @throws Exception
     */
    public static void injectField(Object instance, IocBean iocBean, Ioc ioc) {
        List<Property> properties = iocBean.getPropertiesList();
        if (properties == null) {
            return;
        }
        for (Property property : properties) {
            Class<?> klass = iocBean.getClazz();
            //实例注入
            String ref = property.getRef();
            if (Strings.isNotBlank(ref)) {
                String setMethodName = "set" + Strings.upperFirst(ref);
                try {
                    Object obj = ioc.getBean(ref);
                    
                    Method[] ms = klass.getMethods();
                    for(Method m : ms){
                        if(setMethodName.equals(m.getName())){
                            try{
                                m.invoke(instance, obj);
                                log.debug("注入成功 》》 "+setMethodName);
                                break;
                            }catch (Exception e) {
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("注入失败",e);
                }
            }
            
            String valueStr = property.getValue();
            String fieldName = property.getName();
            Field field = property.getField();
            // 子类给父类注入，没有父类的属性
            if (field == null) {
                String setMethodName = "set" + Strings.upperFirst(fieldName);
                log.debug(setMethodName);
                
                boolean found = false;
                // String
                try {
                    Method m = klass.getMethod(setMethodName, String.class);
                    m.invoke(instance, valueStr);
                    found = true;
                } catch (Exception e) {
                    log.warn(String.format("没有找类 %s 的 %s 属性,尝试用setter方法赋值，默认参数类型为String类型!", klass, fieldName));
                }

                // int
                if (!found) {
                    try {
                        Method m = klass.getMethod(setMethodName, int.class);
                        m.invoke(instance, Strings.toInt(valueStr));
                        found = true;
                    } catch (Exception e) {
                        log.warn(String.format("没有找类 %s 的 %s 属性,尝试用setter方法赋值，默认参数类型为int类型!", klass, fieldName));
                    }
                }

                // boolean
                if (!found && ("true".equalsIgnoreCase(valueStr) || "false".equalsIgnoreCase(valueStr))) {
                    try {
                        Method m = klass.getMethod(setMethodName, boolean.class);
                        m.invoke(instance, Strings.toBoolean(valueStr));
                    } catch (Exception e) {
                        log.warn(String.format("没有找类 %s 的 %s 属性,尝试用setter方法赋值，默认参数类型为boolean类型!", klass, fieldName));
                    }
                }
            }
            // 子类有属性，也就是自己给自己注入
            else {
                Class<?> type = field.getType();
                fieldName = field.getName();
                Object value = null;
                if (Strings.isNotBlank(valueStr)) {
                    value = Json.fromJson(valueStr, type);
                } else {
                    value = ioc.getBean(type);
                }
                try {
                    String setMethodName = "set" + Strings.upperFirst(fieldName);
                    boolean findMethod = false;
                    try {
                        Method m = null;
                        try {
                            m = klass.getMethod(setMethodName, type);
                            m.invoke(instance, value);
                            findMethod = true;
                        } catch (Exception e) {
                            // 使用String类型找，有些人喜欢在set方法中在改变类型
                            m = klass.getMethod(setMethodName, String.class);
                            m.invoke(instance, value);
                            findMethod = true;
                        }
                    } catch (Exception e) {
                    }
                    if (!findMethod) {
                        field.setAccessible(true);
                        field.set(instance, value);
                    }
                } catch (Throwable e) {
                    throw Exceptions.make("反射给属性赋值异常 class : " + klass.getName() + " ,属性名称: " + fieldName, e);
                }
            }
        }
    }

    /**
     * 调用get方法获取实例的属性值
     * 
     * @param instance
     * @param field
     * @return
     */
    public static Object getFieldValue(Object instance, Field field) {
        Class<?> klass = instance.getClass();
        String name = field.getName();
        String methodName = "get" + Strings.upperFirst(name);
        try {
            Method method = klass.getMethod(methodName, null);
            return method.invoke(instance, null);
        } catch (Exception e) {
            throw Exceptions.make(String.format("%s 没有找到%s  方法", klass, methodName), e);
        }
    }
}
