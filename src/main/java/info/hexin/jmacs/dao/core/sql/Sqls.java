package info.hexin.jmacs.dao.core.sql;

import info.hexin.jmacs.dao.DaoRef;
import info.hexin.jmacs.dao.annotation.Table;
import info.hexin.jmacs.dao.entity.FieldInfo;
import info.hexin.lang.Exceptions;
import info.hexin.lang.string.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成sql
 * 
 * @author hexin
 * 
 */
public class Sqls {

    /**
     * 生成insert语句
     * 
     * @param obj
     * @return
     */
    public static SqlWrap insert(Object obj) {
        SqlWrap sqlWrap = new SqlWrap();

        Class<?> klass = obj.getClass();
        Table table = klass.getAnnotation(Table.class);
        if (table != null) {
            String tableName = null;
            if (Strings.isNotBlank(table.value())) {
                tableName = table.value();
            } else {
                tableName = klass.getSimpleName().toUpperCase();
            }
            List<FieldInfo> fieldInfos = DaoRef.getCloumnFiledValue(obj);
            List<Object> param = new ArrayList<Object>();
            if (fieldInfos.size() > 0) {
                StringBuilder sql = new StringBuilder("insert into ");
                StringBuilder p = new StringBuilder("values ( ");
                sql.append(tableName);
                sql.append(" ( ");
                for (int i = 0; i < fieldInfos.size(); i++) {
                    FieldInfo fieldInfo = fieldInfos.get(i);
                    if (i > 0) {
                        sql.append(",");
                        p.append(",");
                    }
                    p.append("?");
                    sql.append(fieldInfo.getName());
                    param.add(fieldInfo.getValue());
                }

                sql.append(")");
                p.append(")");
                sql.append(p.toString());

                sqlWrap.setSql(sql.toString());
                sqlWrap.setParam(param);
                sqlWrap.setSqlType(SqlType.INSERT);

                return sqlWrap;
            } else {
                throw Exceptions.make("属性全部为null值");
            }
        } else {
            throw Exceptions.make(String.format("%s 没有标记Table注解，不能生成sql", klass));
        }
    }
}
