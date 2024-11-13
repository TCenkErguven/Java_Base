package com.hazelcast.service;

import com.hazelcast.dto.SaveRequestDto;
import com.hazelcast.server.proto.DuplicateGrpc;
import com.hazelcast.server.proto.QueryRequest;
import com.hazelcast.server.proto.SaveRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class DuplicateGrpcService extends DuplicateGrpc.DuplicateImplBase {

    private final HazelcastService hazelcastService;

    public DuplicateGrpcService(HazelcastService hazelcastService){
        this.hazelcastService = hazelcastService;
    }

    @Override
    public void queryForMessage(QueryRequest request, StreamObserver<SaveRequest> responseObserver) {
        SaveRequestDto existingDto = hazelcastService.findResponseByUUID(request.getUuid());
        SaveRequest saveRequest = SaveRequest.newBuilder()
                .setMessage(existingDto.getMessage())
                .setUuid(existingDto.getUuid())
                .build();
        try{
            responseObserver.onNext(saveRequest);
            responseObserver.onCompleted();
        }catch(Exception e){
            responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("Error incoming, take cover it from findUUID")));
        }

    }

    @Override
    public void saveMessage(SaveRequest request, StreamObserver<SaveRequest> responseObserver) {
        SaveRequestDto dto = new SaveRequestDto(request.getUuid(), request.getMessage());
        dto = hazelcastService.save(dto);
        SaveRequest saveRequest = SaveRequest.newBuilder()
                .setUuid(dto.getUuid())
                .setMessage(dto.getMessage())
                .build();
        try{
            responseObserver.onNext(saveRequest);
        }catch (Exception e){
            responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("Error incoming, take cover it from saveDto")));
        }
        responseObserver.onCompleted();


    }
}
