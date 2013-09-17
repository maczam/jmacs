package info.hexin.jmacs.dao.tx;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 单个连接描述，
 * 
 * @author hexin
 * 
 */
public class ConnInfo {
    private Connection connection;
    private DataSource dataSource;
    private int oldLevel;
    private boolean autoCommint;

    public ConnInfo(DataSource dataSource, Connection connection) throws SQLException {
        this.dataSource = dataSource;
        this.connection = connection;
        this.oldLevel = connection.getTransactionIsolation();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int getOldLevel() {
        return oldLevel;
    }

    public void setOldLevel(int oldLevel) {
        this.oldLevel = oldLevel;
    }

    public void setOldAutoCommint(boolean autoCommint) {
        this.autoCommint = autoCommint;
    }

    public boolean isAutoCommint() {
        return autoCommint;
    }

    public void setAutoCommint(boolean autoCommint) {
        this.autoCommint = autoCommint;
    }
}
