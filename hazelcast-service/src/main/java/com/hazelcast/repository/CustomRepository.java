package com.hazelcast.repository;

import com.hazelcast.exception.ErrorType;
import com.hazelcast.exception.HazelCastServiceSaveException;
import com.hazelcast.jdbc.CustomRowMapper;
import com.hazelcast.model.Custom;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomRepository implements ICustomRepository<Custom> {

    private final JdbcTemplate jdbcTemplate;

    public CustomRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Custom> findById(String id) {
        String sql = "Select id, message, transaction_uuid FROM custom WHERE id = ?";
        try {
            List<Custom> custom = jdbcTemplate.query(sql, new CustomRowMapper(), id);
            return Optional.ofNullable(custom.getFirst());
        } catch (Exception e){
            throw new HazelCastServiceSaveException(ErrorType.NOT_FOUND);
        }
    }

    @Override
    public Optional<Custom> findByUUId(String uuid) {
        String sql = "Select id, message, transaction_uuid FROM custom WHERE transaction_uuid = ?";
        try {
            List<Custom> custom = jdbcTemplate.query(sql, new CustomRowMapper(), uuid);
            return custom.isEmpty() ? Optional.empty() : Optional.ofNullable(custom.getFirst());
        } catch (Exception e){
            throw new HazelCastServiceSaveException(ErrorType.NOT_FOUND);
        }
    }

    @Override
    public List<Custom> findAll() {
        try {
        String sql = "Select id, message, transaction_uuid FROM custom";
        return jdbcTemplate.query(sql, new CustomRowMapper());
        } catch (Exception e){
            throw new HazelCastServiceSaveException(ErrorType.NOT_FOUND);
        }
    }

    @Override
    public void save(Custom custom) {
        String sql = "INSERT INTO custom (message, transaction_uuid, created_by, updated_by, created, updated) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, custom.getMessage(), custom.getTransactionUUID(), custom.getCreatedBy(), custom.getUpdatedBy(), custom.getCreated(), custom.getUpdated());
    }

    @Override
    public void update(Custom custom) {
        String sql = "UPDATE custom set message = ?, transaction_uuid = ?, updated_by = ?, updated = ? WHERE id = ?";
        jdbcTemplate.update(sql, custom.getMessage(), custom.getTransactionUUID(), custom.getUpdatedBy(), custom.getUpdated(), custom.getId());
    }

    @Override
    public void delete(Custom custom) {
        String sql = "UPDATE custom set deleted = ?, updated_by = ?, updated = ? WHERE id = ?";
        jdbcTemplate.update(sql, System.currentTimeMillis(), custom.getUpdatedBy(), custom.getUpdated(), custom.getId());
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM custom";
        jdbcTemplate.update(sql);
    }
}
