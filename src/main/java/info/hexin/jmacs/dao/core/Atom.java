package info.hexin.jmacs.dao.core;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 原子执行器。
 * 
 * @author hexin
 * 
 */
public interface Atom {

    /**
     * 中间调用的方法都是原子的
     * 
     * @param connection
     */
    void run(Connection connection) throws SQLException;
}
