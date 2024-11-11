package com.java.base.repository.base;

import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public final class GenericBaseRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;

    public GenericBaseRowMapper(Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        try{
                T instance = clazz.getDeclaredConstructor().newInstance();

                Field[] fields = clazz.getDeclaredFields();
                for(Field field : fields){
                    field.setAccessible(true);

                    if (field.getType() == int.class) {
                        field.setInt(instance, rs.getInt(field.getName()));
                    } else if (field.getType() == long.class) {
                        field.setLong(instance, rs.getLong(field.getName()));
                    } else if (field.getType() == double.class) {
                        field.setDouble(instance, rs.getDouble(field.getName()));
                    } else if (field.getType() == float.class) {
                        field.setFloat(instance, rs.getFloat(field.getName()));
                    } else if (field.getType() == short.class) {
                        field.setShort(instance, rs.getShort(field.getName()));
                    } else if (field.getType() == byte.class) {
                        field.setByte(instance, rs.getByte(field.getName()));
                    } else if (field.getType() == boolean.class) {
                        field.setBoolean(instance, rs.getBoolean(field.getName()));
                    } else if (field.getType() == Integer.class) {
                        field.set(instance, rs.getObject(field.getName()));
                    } else if (field.getType() == Long.class) {
                        field.set(instance, rs.getObject(field.getName()));
                    } else if (field.getType() == Double.class) {
                        field.set(instance, rs.getObject(field.getName()));
                    } else if (field.getType() == Float.class) {
                        field.set(instance, rs.getObject(field.getName()));
                    } else if (field.getType() == Short.class) {
                        field.set(instance, rs.getObject(field.getName()));
                    } else if (field.getType() == Byte.class) {
                        field.set(instance, rs.getObject(field.getName()));
                    } else if (field.getType() == Boolean.class) {
                        field.set(instance, rs.getObject(field.getName()));
                    } else if (field.getType() == String.class) {
                        field.set(instance, rs.getString(field.getName()));
                    } else if (field.getType() == java.util.Date.class) {
                        field.set(instance, rs.getTimestamp(field.getName()));
                    } else if (field.getType() == java.util.UUID.class){
                        String uuid = rs.getString(field.getName());
                        field.set(instance, uuid != null ? UUID.fromString(uuid) : null);
                    }
                }

            return instance;

        }catch (Exception ex){
            throw new SQLException("Row Mapper Exception");
        }
    }
}
