package info.hexin.jmacs.dao.tx;

import info.hexin.lang.Exceptions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * 封装单个事务的实体
 * 
 * @author hexin
 * 
 */
public class Transaction {
    private List<ConnInfo> list;

    public Transaction() {
        list = new ArrayList<ConnInfo>();
    }

    public Transaction(ConnInfo connInfo) {
        list = new ArrayList<ConnInfo>();
        list.add(connInfo);
    }

    /**
     * 从连接池中获取一个连接，并传播到这个
     * 
     * @return
     */
    public Connection getConnection(DataSource dataSource) {
        if (list.size() > 0) {
            for (ConnInfo connInfo : list) {
                // 如果该数据源已经保存在该事务中，那么获取之前的连接
                if (connInfo.getDataSource() == dataSource) {
                    return connInfo.getConnection();
                }
            }
            // 没有返回，那么就没有找到
            try {
                Connection connection = dataSource.getConnection();
                ConnInfo connInfo = new ConnInfo(dataSource, connection);
                list.add(connInfo);
                return connection;
            } catch (SQLException e) {
                throw Exceptions.make(e);
            }
        } else {
            throw Exceptions.make("当前事务中 没有保存连接!!");
        }
    }

    /**
     * 获取当前事务所持有的连接
     * 
     * @return
     */
    public Connection getConnection() {
        if (list.size() > 0) {
            return list.get(0).getConnection();
        } else {
            throw Exceptions.make("当前事务中 没有保存连接!!");
        }
    }

    /**
     * 事务提交。 考虑到多个数据源
     */
    public void commint() {
        SQLException exception = null;
        for (ConnInfo connInfo : list) {
            try {
                connInfo.getConnection().commit();
                connInfo.getConnection().setAutoCommit(connInfo.isAutoCommint());
            } catch (SQLException e) {
                exception = e;
                break;
            }
        }

        if (exception != null) {
            throw Exceptions.make(exception);
        }
    }

    /**
     * 事务回滚
     */
    protected void rollback() {
        for (ConnInfo cInfo : list) {
            try {
                cInfo.getConnection().rollback();
            } catch (Throwable e) {
            }
        }
    }

    /**
     * 关闭当前事务所有的连接
     */
    public void close() {
        for (ConnInfo cInfo : list) {
            try {
                cInfo.getConnection().close();
            } catch (Throwable e) {
            }
        }
    }

    public void begin() {
        for (ConnInfo cInfo : list) {
            try {
                Connection connection = cInfo.getConnection();
                boolean autoCommint = connection.getAutoCommit();
                cInfo.setOldAutoCommint(autoCommint);
                if (autoCommint) {
                    connection.setAutoCommit(false);
                }
            } catch (Throwable e) {
            }
        }
    }
}
