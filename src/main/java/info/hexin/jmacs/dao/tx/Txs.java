package info.hexin.jmacs.dao.tx;

import info.hexin.lang.Exceptions;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 
 * @author hexin
 * 
 */
public class Txs {

    private static ThreadLocal<Transaction> trans = new ThreadLocal<Transaction>();
    // 保存上一次获取的连接
    private static ThreadLocal<ConnInfo> connInfo = new ThreadLocal<ConnInfo>();

    /**
     * 获取一个连接
     * 
     * @param ds
     * @return
     * @throws SQLException
     */
    public static Connection getConnection(DataSource ds) throws SQLException {
        Transaction transaction = trans.get();
        if (transaction != null) {
            return transaction.getConnection();
        } else {
            Connection conn = ds.getConnection();
            ConnInfo info = new ConnInfo(ds, conn);
            connInfo.set(info);
            return conn;
        }
    }

    /**
     * 如果事务
     */
    public static void begin() {
        Transaction transaction = trans.get();
        if (transaction == null) {
            if (connInfo.get() == null) {
                throw Exceptions.make("当前线程没有获取数据库 连接 不能开启事务.");
            }
            transaction = new Transaction(connInfo.get());
            transaction.begin();
        }
        trans.set(transaction);
    }

    /**
     * 提交当前事务，异常回滚的话自动抛上去
     */
    public static void commit() {
        Transaction transaction = trans.get();
        if (transaction != null) {
            transaction.commint();
        }
    }

    /**
     * 关闭
     */
    public static void close() {
        Transaction transaction = trans.get();
        if (transaction != null) {
            transaction.close();
            trans.set(null);
        }
    }

    public static void rollback() {
        Transaction transaction = trans.get();
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
