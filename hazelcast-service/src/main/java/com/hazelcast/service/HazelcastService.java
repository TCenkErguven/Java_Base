package com.hazelcast.service;

import com.hazelcast.model.Custom;
import com.hazelcast.server.proto.SaveRequest;
import org.springframework.stereotype.Service;

@Service
public class HazelcastService {
    private final JdbcService jdbcService;
    private final CacheService cacheService;

    public HazelcastService(JdbcService jdbcService, CacheService cacheService) {
        this.jdbcService = jdbcService;
        this.cacheService = cacheService;
    }

    // #TODO Logger will be added instead of system.out and Impl file configuration will be added soon...
    // #TODO Unit test will be added
    // #TODO Integration tests will be added
    // #TODO Load tests will be added
    // #TODO CacheManager null ise hazelcast-client ı initialize dene

    public void validateRequestUUID(SaveRequest request) throws InterruptedException {
        Custom existingCustom = findByTransactionalUUID(request.getUuid());

        if (existingCustom == null) {
            Custom custom = new Custom();
            custom.setTransactionUUID(request.getUuid());
            custom.setMessage(request.getMessage());
            save(custom);
        }
    }


    public Custom findByTransactionalUUID(String uuid) throws InterruptedException {

        Custom custom = null;
        try{
            custom = cacheService.cacheableFindByUUID(uuid);
        }catch (Exception e){
            custom = jdbcService.findByUUId(uuid);
        }

        if (custom != null && !custom.getIsProgressCompleted()){
            Thread.sleep(100);
            findByTransactionalUUID(uuid);
        }

        return custom;
    }



    public void save(Custom custom) {
        try {
            cacheService.cacheableSave(custom);
        } catch (Exception e) {
            jdbcService.save(custom);
        }
    }

    public void update(SaveRequest request) {
        try {
            Custom custom = cacheService.cacheableFindByUUID(request.getUuid());
            custom.setMessage(request.getMessage());
            cacheService.cacheableUpdate(custom);
        } catch (Exception e) {
            Custom custom = jdbcService.findByUUId(request.getUuid());
            custom.setMessage(request.getMessage());
            jdbcService.update(custom);
        }
    }



}
