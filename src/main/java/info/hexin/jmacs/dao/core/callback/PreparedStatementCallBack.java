package info.hexin.jmacs.dao.core.callback;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author hexin
 * 
 * @param <T>
 */
public abstract class PreparedStatementCallBack<T> {

    public abstract T doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException;
}
