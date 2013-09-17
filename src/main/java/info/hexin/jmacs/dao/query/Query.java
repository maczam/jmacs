package info.hexin.jmacs.dao.query;

import info.hexin.lang.Exceptions;

/**
 * 封装查询条件
 * 
 * @author hexin
 * 
 */
public class Query {

    private final Object NOT_SET = new Object();
    private Object value = NOT_SET;

    public static Query where(String string) {
        return null;
    }

    public Query is(Object value) {
        if (value != NOT_SET) {
            throw Exceptions.make("已经赋过值了");
        }
        this.value = value;
        return this;
    }

    public Query is(Query[] query) {
        return null;
    }
}
