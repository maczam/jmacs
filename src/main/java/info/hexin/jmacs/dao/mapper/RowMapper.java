package info.hexin.jmacs.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
