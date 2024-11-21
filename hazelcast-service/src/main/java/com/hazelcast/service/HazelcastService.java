package com.hazelcast.service;

import com.hazelcast.exception.ErrorType;
import com.hazelcast.exception.HazelCastServiceSaveException;
import com.hazelcast.exception.HazelCastServiceUpdateException;
import com.hazelcast.model.Custom;
import com.hazelcast.server.proto.FindUUIDRequest;
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

    public Custom validateRequestUUID(FindUUIDRequest request) {
        Custom existingCustom = null;
        try{
            existingCustom = cacheService.cacheableFindByUUID(request.getUuid());
        }catch (Exception e){
            System.out.println(e);
        }
        return existingCustom;
    }



    public Custom save(Custom custom) {
        try {
            custom = cacheService.cacheableSave(custom);
        } catch (Exception e) {
            throw new HazelCastServiceSaveException(ErrorType.INTERNAL_ERROR);
        }
        return custom;
    }

    public Custom update(SaveRequest request) {
        Custom custom = null;
        try {
            custom = cacheService.cacheableFindByUUID(request.getUuid());
            custom.setMessage(request.getCustom());
            cacheService.cacheableUpdate(custom);
        } catch (Exception e) {
            throw new HazelCastServiceUpdateException(ErrorType.INTERNAL_ERROR);
        }
        return custom;
    }



}
