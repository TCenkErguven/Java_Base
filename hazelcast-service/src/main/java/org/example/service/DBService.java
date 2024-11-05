package org.example.service;

import org.example.dto.SaveRequestDto;
import org.springframework.stereotype.Service;

@Service
public class DBService {
    public String save(SaveRequestDto dto){
        // throw new HazelCastServiceException(ErrorType.INTERNAL_ERROR);

        try{
            System.out.println("Saved");
            return dto.getName();
        }catch (Exception ex) {
            System.out.println("Failed");
            return "Failed";
        }

    }


    public SaveRequestDto findByUUId(String id){
        try{
            System.out.println("Find the entity from db");
            SaveRequestDto dto = new SaveRequestDto("id","2122-2313-3213-3213");
            return dto;
        }catch (Exception ex) {
            System.out.println("Failed");
            return null;
        }
    }
}
