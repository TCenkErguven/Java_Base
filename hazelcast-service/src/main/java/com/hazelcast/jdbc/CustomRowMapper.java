package com.hazelcast.jdbc;

import com.hazelcast.model.Custom;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomRowMapper implements RowMapper<Custom> {
    @Override
    public Custom mapRow(ResultSet rs, int rowNum) throws SQLException {
        Custom custom = new Custom();
        custom.setId(rs.getString("id"));
        custom.setMessage(rs.getString("message"));
        custom.setTransactionUUID(rs.getString("transaction_uuid"));
        return custom;
    }
}
