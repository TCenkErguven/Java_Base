package com.hazelcast.service;

import com.hazelcast.exception.ErrorType;
import com.hazelcast.exception.HazelCastServiceSaveException;
import com.hazelcast.exception.HazelCastServiceUpdateException;
import com.hazelcast.model.Custom;
import com.hazelcast.repository.CustomRepository;
import org.springframework.stereotype.Service;

@Service
public class JdbcService {
    private final CustomRepository repository;

    public JdbcService(CustomRepository repository){
        this.repository = repository;
    }

    // #TODO Mapstruct will be added


    public Custom save(Custom custom){
        try{
            repository.save(custom);
            System.out.println("Saved");
            return custom;
        }catch (Exception ex) {
            System.out.println("Failed Save");
            throw new HazelCastServiceSaveException(ErrorType.INTERNAL_ERROR);
        }
    }


    public Custom findByUUId(String uuid){
        try{
            return repository.findByUUId(uuid)
                    .orElseThrow(() -> new HazelCastServiceSaveException(ErrorType.NOT_FOUND));
        }catch (Exception ex) {
            System.out.println("Failed");
            return null;
        }
    }

    public Custom update(Custom custom){
        try{
            custom.setIsProgressCompleted(true);
            repository.update(custom);
            System.out.println("Update");
            return custom;
        }catch (Exception ex){
            System.out.println("Failed Update");
            throw new HazelCastServiceUpdateException(ErrorType.INTERNAL_ERROR);
        }
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
