package info.hexin.jmacs.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * RowMapper执行器
 * 
 * @author hexin
 * 
 * @param <T>
 */
public class RowMapperExtractor<T> {
    private final RowMapper<T> rowMapper;

    public RowMapperExtractor(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    public List<T> extractData(ResultSet rs) throws SQLException {
        List<T> results = new ArrayList<T>();
        int rowNum = 0;
        while (rs.next()) {
            results.add(this.rowMapper.mapRow(rs, rowNum++));
        }
        return results;
    }
}
