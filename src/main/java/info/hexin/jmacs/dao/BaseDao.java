package info.hexin.jmacs.dao;

import info.hexin.jmacs.dao.core.Atom;
import info.hexin.jmacs.dao.core.AtomResult;
import info.hexin.jmacs.dao.core.callback.ConnectionCallback;
import info.hexin.jmacs.dao.tx.Txs;
import info.hexin.jmacs.ioc.annotation.Inject;
import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.lang.Exceptions;

import java.sql.Connection;

import javax.sql.DataSource;

/**
 * 
 * @author hexin
 * 
 */
public class BaseDao {
    private static Log log = Logs.get();

    private DataSource dataSource;

    public BaseDao() {

    }

    public BaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        if (this.dataSource != null) {

            if (log.isErrorEnabled()) {
                log.error("a runing DataSource will be replaced");
            }
        }
        this.dataSource = dataSource;
    }

    public <T> T execute(ConnectionCallback<T> callback) {
        Connection conn = null;
        try {
            conn = Txs.getConnection(dataSource);
            return callback.doInConnection(conn);
        } catch (Exception e) {
            throw Exceptions.make(e);
        }
    }

    void execute(Atom... atom) {
        try {
            Connection conn = Txs.getConnection(dataSource);
            Txs.begin();
            for (Atom a : atom) {
                a.run(conn);
            }
            Txs.commit();
        } catch (Exception e) {
            Txs.rollback();
            throw Exceptions.make(e);
        } finally {
            Txs.close();
        }
    }

    <T> T execute(AtomResult<T> atom) {
        try {
            Connection conn = Txs.getConnection(dataSource);
            Txs.begin();
            T t = atom.run(conn);
            Txs.commit();
            return t;
        } catch (Exception e) {
            Txs.rollback();
            throw Exceptions.make(e);
        } finally {
            Txs.close();
        }
    }
}
