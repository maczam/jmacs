package info.hexin.jmacs.dao.core;

import info.hexin.jmacs.dao.Dao;
import info.hexin.jmacs.dao.core.callback.ConnectionCallback;
import info.hexin.jmacs.dao.core.model.A;
import info.hexin.jmacs.ioc.context.Ioc;
import info.hexin.jmacs.ioc.context.impl.SimpleIoc;
import info.hexin.jmacs.ioc.loader.impl.XmlLoader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.Test;

public class DAO_TX_Test {
//    Ioc ioc = new SimpleIoc(new XmlLoader("c3p0.xml"));
    Ioc ioc = new SimpleIoc(new XmlLoader("druid.xml"));
    Dao dao = new Dao(ioc.getBean(DataSource.class));
//    @Test
    public void testDaoLoad() throws SQLException {
        dao.execute(new ConnectionCallback<Object>() {
            @Override
            public Object doInConnection(Connection conn) throws SQLException {
                // t_common_member
                Statement sql_statement = conn.createStatement();
                String query = "select * from t_common_member";
                ResultSet result = sql_statement.executeQuery(query);
                while (result.next()) {
                    String number = result.getString("username");
                    String name = result.getString("email");
                    String mathScore = result.getString("password");
                    // 取得数据库中的数据
                    System.out.println(" " + number + " " + name + " " + mathScore);
                }
                return null;
            }
        });
    }
    
    
//    @Test
    public void test_dao_query(){
        A a =  dao.queryOne("select * from user where name = ?",new Object []{"hexin1"},A.class);
        System.out.println(a.getName());
        System.out.println(a.getBrithday());
    }
    @Test
    public void test_dao_insert(){
        A a = new A();
        a.setBrithday(new Date());
        a.setName("hexin1");
        a.setPassword("hexin1");
        A user1 = dao.insert(a);
    }
}
