package info.hexin.jmacs.dao.core;

import java.sql.Connection;
import java.sql.SQLException;

public interface AtomResult<T> {

    /**
     * 中间调用的方法都是原子的 有返回结果
     * 
     * @param connection
     */
    T run(Connection connection) throws SQLException;
}
