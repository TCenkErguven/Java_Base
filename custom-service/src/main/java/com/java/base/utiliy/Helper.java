package com.java.base.utiliy;

import java.lang.reflect.Field;
import java.util.StringJoiner;
import java.util.UUID;

public class Helper {

    public static String getFieldGenericNames(Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();
        StringJoiner stringJoiner = new StringJoiner(",");
        for (Field fieldName : fields) {
            stringJoiner.add(fieldName.getName());
        }
        return stringJoiner.toString();
    }

    public static String getFieldGenericMarks(Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();
        StringJoiner stringJoiner = new StringJoiner(",");
        for (int i = 0; i < fields.length; i++){
            stringJoiner.add("?");
        }
        return stringJoiner.toString();
    }

    public static Object[] getFieldValues(Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();
        Object[] values = new Object[fields.length];

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true); // Make private fields accessible
            try {
                if(fields[i].getName().equals("id") && fields[i].get(obj) == null) {
                    values[i] = UUID.randomUUID();
                    continue;
                }
                values[i] = fields[i].get(obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field value: " + fields[i].getName(), e);
            }
        }
        return values;
    }

    public static String getTableName(String input){
        StringBuilder result = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                if (!result.isEmpty()) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}
