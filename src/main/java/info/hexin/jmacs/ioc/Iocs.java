package info.hexin.jmacs.ioc;

import info.hexin.jmacs.ioc.annotation.Inject;
import info.hexin.lang.Exceptions;
import info.hexin.lang.string.Strings;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * ioc容器工具方法
 *
 * @author hexin
 */
public class Iocs {

    /**
     * 获取class需要注入的属性，不获取父类
     *
     * @param clazz
     * @return
     */
    public static List<Property> getPropertiesList(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<Property> propertiesList = new ArrayList<Property>();
        for (Field field : fields) {
            Inject inject = field.getAnnotation(Inject.class);
            if (inject != null) {
                String byName = inject.name();
                String value = inject.value();
                if (inject != null) {
                    Class<?> fieldClazz = field.getType();
                    if (Strings.isBlank(byName)) {
                        String simpleName = fieldClazz.getSimpleName();
                        byName = Strings.lowerFirst(simpleName);
                    }
                    Property property = new Property();
                    property.setField(field);
                    property.setRef(byName);
                    property.setValue(value);
                    propertiesList.add(property);
                }
            }
        }
        return propertiesList;
    }

    public static Field findClassFiled(Class<?> klass, String fieldName) {

        Field field = null;
        try {
            // 先找当前、父类、或者接口的public属性
            try {
                // 接口的属性不能注入
                // field = klass.getField(fieldName);
                field = klass.getDeclaredField(fieldName);
            } catch (Exception e) {
                // 没有找到
            }
            if (field == null) {
                // 获取当前类的public、private和proteced 但是不包括父类的，
                while (field == null && (klass = klass.getSuperclass()) != null) {
                    try {
                        field = klass.getDeclaredField(fieldName);
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw Exceptions.make(e);
        }
        return field;
    }
}
