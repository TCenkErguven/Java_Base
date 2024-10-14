package com.java.base.utility;

import java.util.UUID;

public class Helper {

    public static String generateCode(){
        String code = UUID.randomUUID().toString();
        String[] data = code.split("-");
        StringBuilder newCode= new StringBuilder();
        for (String str : data){
            newCode.append(str.charAt(0));
        }
        return newCode.toString();
    }

}
