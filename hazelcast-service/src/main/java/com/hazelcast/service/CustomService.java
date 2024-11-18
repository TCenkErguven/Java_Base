package com.hazelcast.service;

import com.hazelcast.dto.SaveRequestDto;
import com.hazelcast.exception.ErrorType;
import com.hazelcast.exception.HazelCastServiceException;
import com.hazelcast.model.Custom;
import com.hazelcast.repository.CustomRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomService {
    private final CustomRepository repository;

    public CustomService(CustomRepository repository){
        this.repository = repository;
    }

    // #TODO Mapstruct will be added


    public SaveRequestDto save(SaveRequestDto dto){
        try{
            Custom custom = new Custom();
            custom.setTransactionUUID(dto.getUuid());
            custom.setMessage(dto.getMessage());
            repository.save(custom);
            System.out.println("Saved");
            return dto;
        }catch (Exception ex) {
            System.out.println("Failed");
            throw new HazelCastServiceException(ErrorType.INTERNAL_ERROR);
        }
    }


    public SaveRequestDto findByUUIdAndReturnDto(String uuid){
        try{
            Custom custom = repository.findByUUId(uuid)
                    .orElseThrow(() -> new HazelCastServiceException(ErrorType.NOT_FOUND));
            return new SaveRequestDto(custom.getTransactionUUID(),custom.getMessage());
        }catch (Exception ex) {
            System.out.println("Failed");
            return null;
        }
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
