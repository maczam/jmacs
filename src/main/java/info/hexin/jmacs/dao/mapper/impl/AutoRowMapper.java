package info.hexin.jmacs.dao.mapper.impl;

import info.hexin.jmacs.dao.DaoRef;
import info.hexin.jmacs.dao.mapper.RowMapper;
import info.hexin.lang.Exceptions;
import info.hexin.lang.reflect.Reflects;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * 系统默认自动的和ResultSet匹配
 * 
 * @author hexin
 * 
 * @param <T>
 */
public class AutoRowMapper<T> implements RowMapper<T> {

    private Class<T> klazz;

    public AutoRowMapper(Class<T> klazz) {
        this.klazz = klazz;
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        System.out.println(rs.getClass());
        ResultSetMetaData metaData = rs.getMetaData();
        Map<String, Field> fieldMap = DaoRef.getCloumnFiled(klazz);
        try {
            T t;
            try {
                t = klazz.newInstance();
            } catch (Exception e) {
                throw Exceptions.make("请检查POJO 是否可以实例化", e);
            }
            int count = metaData.getColumnCount();
            for (int i = 1; i <= count; i++) {
                String columnName = metaData.getColumnLabel(i);
                if (fieldMap.containsKey(columnName)) {
                    Field field = fieldMap.get(columnName);
                    Class<?> fieldType = field.getType();
                    Object value = null;
                    if (fieldType == String.class) {
                        value = rs.getString(columnName);
                    } else if (fieldType == Date.class) {
                        value = rs.getDate(columnName);
                    } else if (fieldType == int.class || fieldType == Integer.class) {
                        value = rs.getInt(columnName);
                    } else if (fieldType == long.class || fieldType == Long.class) {
                        value = rs.getLong(columnName);
                    } else if (fieldType == float.class || fieldType == Float.class) {
                        value = rs.getFloat(columnName);
                    } else if (fieldType == double.class || fieldType == Double.class) {
                        value = rs.getDouble(columnName);
                    } else if (fieldType == short.class || fieldType == Short.class) {
                        value = rs.getShort(columnName);
                    } else if (fieldType == byte.class || fieldType == Byte.class) {
                        value = rs.getByte(columnName);
                    } else if (fieldType == byte[].class || fieldType == Byte[].class) {
                        value = rs.getBytes(columnName);
                    }
                    System.out.println(columnName + "  " + fieldType + "   " + value);
                    Reflects.setFieldValue(t, field, value);
                }
            }
            return t;
        } catch (Exception e) {
            throw Exceptions.make("自动给属性赋值异常", e);
        }
    }
}
