package com.grpc.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grpc.client.dto.FindByUUIDResponseDto;
import com.grpc.client.dto.SaveRequestDto;
import com.grpc.client.dto.SaveResponseDto;
import com.grpc.client.proto.SimpleGrpc;
import com.grpc.client.utility.GrpcHelper;
import com.hazelcast.server.proto.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrpcService {

    @GrpcClient("greetingService")
    private SimpleGrpc.SimpleBlockingStub simpleBlockingStub;

    @GrpcClient("duplicateService")
    private DuplicateGrpc.DuplicateBlockingStub duplicateBlockingStub;

    @Autowired
    private ObjectMapper objectMapper;

    public SaveResponseDto save(SaveRequestDto dto){

        ResponseWrapper grpcResponse = null;
        try{
            //#TODO duruma göre struct yapıp ekleme yapılacak
            grpcResponse = duplicateBlockingStub.saveMessage(SaveRequest.newBuilder()
                    .setUuid(dto.getUuid())
                    .build());
        }catch (Exception e){
            System.out.println(e);
        }

        SaveResponseDto response = new SaveResponseDto();

        if(grpcResponse != null) {
            response.setCode(grpcResponse.getStatus().getCode());
            response.setStatus(grpcResponse.getStatus().getStatus());
            response.setErrorMessage(grpcResponse.getStatus().getMessage());
        }

        return response;
    }

    public FindByUUIDResponseDto findByUUID(String uuid){
        ResponseWrapper grpcResponse = null;
        try{
            grpcResponse = duplicateBlockingStub.queryForMessage(FindUUIDRequest.newBuilder()
                    .setUuid(uuid)
                    .build());
        }catch (Exception e){
            System.out.println(e);
        }

        FindByUUIDResponseDto response = new FindByUUIDResponseDto();
        if(grpcResponse != null) {
            response.setCode(grpcResponse.getStatus().getCode());
            response.setStatus(grpcResponse.getStatus().getStatus());
            response.setErrorMessage(grpcResponse.getStatus().getMessage());
            response.setUuid(grpcResponse.getCustom().getUuid());
            response.setMessage(grpcResponse.getCustom().getTransactionMessage());

            Struct struct = grpcResponse.getDataMap().get("custom").getStructValue();
            String extractedUuid = struct.getFieldsMap().get("uuid").getStringValue();
            String extractedMessage = struct.getFieldsMap().get("transactionMessage").getStringValue();

            response.setUuid(extractedUuid);
            response.setMessage(extractedMessage);

        }
        return response;
    }

}
