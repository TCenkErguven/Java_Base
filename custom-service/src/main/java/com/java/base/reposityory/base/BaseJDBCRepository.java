package com.java.base.reposityory.base;

import com.java.base.utiliy.Helper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseJDBCRepository<T,ID> implements BaseDao<T,ID> {
    private final JdbcTemplate jdbcTemplate;

    public BaseJDBCRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public T save(T t) {
        String fieldNames = Helper.getFieldGenericNames(t);
        String sql = "INSERT INTO " + Helper.getTableName(t.getClass().getSimpleName()) + " (" + fieldNames + ") VALUES (" + Helper.getFieldGenericMarks(t) + ")";

        try{
            jdbcTemplate.update(sql, Helper.getFieldValues(t));
        } catch (DataAccessException e){
            throw new RuntimeException("Error saving entity: " + e.getMessage(), e);
        }
        return t;
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t){
        return null;
    }

    @Override
    public T update(T t){
        return null;
    }

    @Override
    public void delete(T t){

    }

    @Override
    public void deleteById(ID id){

    }

    @Override
    public List<T> findAll(){
        return null;
    }

    @Override
    public Optional<T> findById(UUID id){
        String sql = String.format("SELECT * FROM %s WHERE id = ?", Helper.getTableName(getGenericType().getSimpleName()));

        try{
            List<T> results = jdbcTemplate.query(sql, new GenericBaseRowMapper<>(getGenericType()), id);
            return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
        } catch (DataAccessException e){
            throw new RuntimeException("Error finding entity: " + e.getMessage(), e);
        }
    }

    protected Class<T> getGenericType() throws TypeNotPresentException, MalformedParameterizedTypeException {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

}
