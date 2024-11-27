package com.hazelcast.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.exception.ErrorType;
import com.hazelcast.exception.HazelCastServiceSaveException;
import com.hazelcast.exception.HazelCastServiceUpdateException;
import com.hazelcast.model.Custom;
import com.hazelcast.server.proto.FindUUIDRequest;
import com.hazelcast.server.proto.SaveRequest;
import org.jetbrains.kotlin.com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

@Service
public class HazelcastService {
    private final JdbcService jdbcService;
    private final CacheService cacheService;
    private final ObjectMapper objectMapper;

    public HazelcastService(JdbcService jdbcService,
                            CacheService cacheService,
                            ObjectMapper objectMapper) {
        this.jdbcService = jdbcService;
        this.cacheService = cacheService;
        this.objectMapper = objectMapper;
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
            JsonNode jsonObject = objectMapper.createObjectNode()
                    .put("transactionMessage", request.getCustom().getTransactionMessage())
                    .put("uuid", request.getCustom().getUuid());
            custom.setMessage(objectMapper.writeValueAsString(jsonObject));
            cacheService.cacheableUpdate(custom);
        } catch (Exception e) {
            throw new HazelCastServiceUpdateException(ErrorType.INTERNAL_ERROR);
        }
        return custom;
    }



}
