package info.hexin.jmacs.dao.core.callback;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 给你一个connection
 * 
 * @author hexin
 * 
 */
public interface ConnectionCallback<T> {

    T doInConnection(Connection conn) throws SQLException;
}
