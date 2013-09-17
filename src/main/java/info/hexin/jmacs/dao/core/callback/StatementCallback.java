package info.hexin.jmacs.dao.core.callback;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author hexin
 * 
 */
public interface StatementCallback<T> {

    public T doInStatement(Statement stmt) throws SQLException;
}
