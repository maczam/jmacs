package info.hexin.jmacs.dao;

import info.hexin.jmacs.dao.core.AtomResult;
import info.hexin.jmacs.dao.core.callback.ConnectionCallback;
import info.hexin.jmacs.dao.core.callback.PreparedStatementCallBack;
import info.hexin.jmacs.dao.core.callback.StatementCallback;
import info.hexin.jmacs.dao.core.sql.SqlWrap;
import info.hexin.jmacs.dao.core.sql.Sqls;
import info.hexin.jmacs.dao.mapper.RowMapper;
import info.hexin.jmacs.dao.mapper.RowMapperExtractor;
import info.hexin.jmacs.dao.mapper.impl.AutoRowMapper;
import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.lang.Exceptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

/**
 * 框架的dao类
 * 
 * @author hexin
 * 
 */
public class Dao extends BaseDao {

    private static Log log = Logs.get();

    public Dao() {
    }

    public Dao(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 
     * @param t
     * @return
     */
    public <T> T insert(T t) {
        final SqlWrap sqlWrap = Sqls.insert(t);
        System.out.println(sqlWrap.getSql());
        final Object ret = execute(new AtomResult<Object>() {
            @Override
            public Object run(Connection connection) throws SQLException {
                Object ret = null;
                PreparedStatement state = connection.prepareStatement(sqlWrap.getSql(),
                        PreparedStatement.RETURN_GENERATED_KEYS);
                List<Object> paramList = sqlWrap.getParam();
                if (null != paramList && paramList.size() > 0) {
                    DaoRef.pstmtAssignment(state, sqlWrap.getParam().toArray());
                }
                state.executeUpdate();
                ResultSet rs = state.getGeneratedKeys();
                if (rs.next()) {
                    ret = rs.getObject(1);
                }
                return ret;
            }
        });
        System.out.println(ret);
        return t;
    }

    public <T> List<T> query(final String sql, final Object[] args, final Class<T> class1) {
        log.debug(String.format("查询语句 %s ,%s  ", sql, class1));
        return execute(new ConnectionCallback<List<T>>() {
            @Override
            public List<T> doInConnection(Connection conn) {
                try {
                    PreparedStatement p = conn.prepareStatement(sql);
                    DaoRef.pstmtAssignment(p, args);
                    ResultSet resultSet = p.executeQuery();
                    RowMapperExtractor<T> extractor = new RowMapperExtractor<T>(new AutoRowMapper<T>(class1));
                    return extractor.extractData(resultSet);
                } catch (Exception e) {
                    throw Exceptions.make(e);
                }
            }
        });
    }

    public <T> List<T> query(final String sql, final Object[] args, final Class<T> class1, final RowMapper<T> rowMapper) {
        log.debug(String.format("查询语句 %s ,%s  ", sql, class1));
        return execute(new ConnectionCallback<List<T>>() {
            @Override
            public List<T> doInConnection(Connection conn) {
                try {
                    PreparedStatement p = conn.prepareStatement(sql);
                    DaoRef.pstmtAssignment(p, args);
                    ResultSet resultSet = p.executeQuery();
                    RowMapperExtractor<T> extractor = new RowMapperExtractor<T>(rowMapper);
                    return extractor.extractData(resultSet);
                } catch (Exception e) {
                    throw Exceptions.make(e);
                }
            }
        });
    }

    public <T> T queryOne(final String sql, final Object[] args, final Class<T> class1) {
        List<T> list = query(sql, args, class1);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public <T> T execute(final StatementCallback<T> statementCallback) {
        return execute(new ConnectionCallback<T>() {
            @Override
            public T doInConnection(Connection conn) throws SQLException {
                Statement statement = conn.createStatement();
                try {
                    return statementCallback.doInStatement(statement);
                } catch (SQLException e) {
                    throw e;
                } finally {
                    if (statement != null) {
                        statement.close();
                    }
                }
            }
        });
    }

    public <T> T execute(final String sql, final PreparedStatementCallBack<T> preparedStatementCallBack) {
        return execute(new AtomResult<T>() {
            @Override
            public T run(Connection connection) throws SQLException {
                PreparedStatement state = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                try {
                    return preparedStatementCallBack.doInPreparedStatement(state);
                } catch (SQLException e) {
                    throw e;
                } finally {
                    if (state != null) {
                        state.close();
                    }
                }
            }
        });
    }

    public int execute(final String sql) {
        return execute(new AtomResult<Integer>() {
            @Override
            public Integer run(Connection connection) throws SQLException {
                PreparedStatement statement = connection.prepareStatement(sql);
                try {
                    return statement.executeUpdate();
                } catch (SQLException e) {
                    throw e;
                } finally {
                    if (statement != null) {
                        statement.close();
                    }
                }
            }
        });
    }
}
