package com.java.base.reposityory;


import com.java.base.model.Custom;
import com.java.base.reposityory.base.BaseJDBCRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CustomRepository extends BaseJDBCRepository<Custom, UUID> {

    public CustomRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

}
