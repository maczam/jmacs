package info.hexin.jmacs.dao.core;

import java.sql.SQLException;

import info.hexin.jmacs.dao.Dao;
import info.hexin.jmacs.dao.core.model.AModel;

import org.junit.Test;

public class DaoTest {

    @Test
    public void testInsertSql() throws SQLException {
        AModel model = new AModel();
        model.setId("id");
        model.setName("aaaa");
    }
}
