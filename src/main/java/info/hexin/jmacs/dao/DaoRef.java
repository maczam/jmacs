package info.hexin.jmacs.dao;

import info.hexin.jmacs.dao.annotation.Column;
import info.hexin.jmacs.dao.entity.FieldInfo;
import info.hexin.lang.Lang;
import info.hexin.lang.reflect.Reflects;
import info.hexin.lang.string.Strings;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dao中常用反射，主要提升性能
 * 
 * @author hexin
 * 
 */
public class DaoRef {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 保存起来，提升部分性能
    static Map<Class<?>, Map<String, Field>> cloumnFiledMap = new HashMap<Class<?>, Map<String, Field>>();

    /**
     * 属性没有表
     * 
     * @param klazz
     * @return
     */
    public static Map<String, Field> getCloumnFiled(Class<?> klazz) {
        if (cloumnFiledMap.containsKey(klazz)) {
            return cloumnFiledMap.get(klazz);
        } else {
            Field[] fields = klazz.getDeclaredFields();
            Map<String, Field> map = new HashMap<String, Field>();
            for (Field field : fields) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    String columnName = null;
                    if (Strings.isNotBlank(column.value())) {
                        columnName = column.value();
                    } else {
                        columnName = field.getName();
                    }
                    map.put(columnName, field);
                }
            }
            cloumnFiledMap.put(klazz, map);
            return map;
        }
    }

    /**
     * 属性没有表
     * 
     * @param klazz
     * @return
     */
    public static List<FieldInfo> getCloumnFiledValue(Object instance) {
        List<FieldInfo> list = new ArrayList<FieldInfo>();
        Map<String, Field> fieldMap = getCloumnFiled(instance.getClass());
        if (fieldMap.size() > 0) {
            for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
                Field field = entry.getValue();
                Object value = Reflects.getFieldValue(instance, field);
                if (!Lang.isNull(value)) {
                    FieldInfo fieldInfo = new FieldInfo();
                    fieldInfo.setName(entry.getKey());
                    fieldInfo.setValue(value);
                    fieldInfo.setType(field.getType());
                    list.add(fieldInfo);
                }
            }
        }
        return list;
    }

    /**
     * 
     * @param p
     * @param args
     * @throws SQLException
     */
    public static void pstmtAssignment(PreparedStatement p, final Object[] args) throws SQLException {
        if (null != args && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                Object o = args[i];
                Class<?> c = o.getClass();
                if (String.class == c) {
                    p.setString(i + 1, o.toString());
                } else if (c == int.class || Integer.class == c) {
                    p.setInt(i + 1, Lang.toInt(o));
                } else if (c == long.class || Long.class == c) {
                    p.setLong(i + 1, Lang.toLong(o));
                } else if (c == boolean.class || c == Boolean.class) {
                    p.setBoolean(i + 1, Lang.toBoolean(o));
                } else if (c == char.class || c == Character.class) {
                } else if (c == double.class || c == Double.class) {
                    p.setDouble(i + 1, Lang.toDouble(o));
                } else if (c == float.class || c == Float.class) {
                    p.setFloat(i + 1, Lang.toFloat(o));
                } else if (c == byte.class || c == Byte.class) {
                    p.setByte(i + 1, Lang.toByte(o));
                } else if (c == short.class || c == Short.class) {
                    p.setShort(i + 1, Lang.toShort(o));
                } else if (c == Date.class) {
                    p.setString(i + 1, sdf.format(o));
                } else {
                    p.setString(i + 1, o.toString());
                }
            }
        }
    }
}
