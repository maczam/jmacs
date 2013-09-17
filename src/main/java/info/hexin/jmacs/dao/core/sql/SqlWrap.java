package info.hexin.jmacs.dao.core.sql;

import java.util.List;

/**
 * 包装sql
 * 
 * @author hexin
 * 
 */
public class SqlWrap {

    SqlType sqlType;
    String sql;
    List<Object> param;

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getParam() {
        return param;
    }

    public void setParam(List<Object> param) {
        this.param = param;
    }
}
