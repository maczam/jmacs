package info.hexin.jmacs.dao.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import info.hexin.jmacs.dao.Dao;
import info.hexin.jmacs.dao.core.callback.PreparedStatementCallBack;
import info.hexin.jmacs.dao.core.model.A;
import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.ioc.context.impl.SimpleIoc;
import info.hexin.jmacs.ioc.loader.impl.XmlLoader;

import javax.sql.DataSource;

import org.junit.Test;

public class DAO_TX_Test1 {
    Ioc ioc = new SimpleIoc(new XmlLoader("druid.xml"));
    Dao dao = new Dao(ioc.getBean(DataSource.class));

    // @Test
    public void test() {
        A a = new A();
        a.setBrithday(new Date());
        a.setName("hexin1");
        a.setPassword("hexin1");
        A user1 = dao.insert(a);
    }

//    @Test
    public void test1() {
        String sql = "update user set name = 'xx22' where id = ?";
        dao.execute(sql, new PreparedStatementCallBack<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.execute("update user set name = 'xx2' where id = 3");
                preparedStatement.setInt(1, 3);
                preparedStatement.execute();
                return null;
            }
        });
    }
    
    @Test
    public void test2(){
        String sql = "update user set name = 'xxxx' where id = 3";
        dao.execute(sql);
    }
}
