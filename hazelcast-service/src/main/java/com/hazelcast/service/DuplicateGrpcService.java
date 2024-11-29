package com.hazelcast.service;

import com.hazelcast.dto.SaveRequestDto;
import com.hazelcast.server.proto.*;
import com.hazelcast.utility.ProtoHelper;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.io.IOException;

@GrpcService
public class DuplicateGrpcService extends DuplicateGrpc.DuplicateImplBase {

    private final HazelcastService hazelcastService;

    public DuplicateGrpcService(HazelcastService hazelcastService){
        this.hazelcastService = hazelcastService;
    }

    @Override
    public void queryForMessage(FindUUIDRequest request, StreamObserver<ResponseWrapper> responseObserver) {
        SaveRequestDto existingDto = hazelcastService.findResponseByUUID(request.getUuid());
        try{
            responseObserver.onNext(ResponseWrapper
                    .newBuilder()
                    .setData(ProtoHelper.fromJson(existingDto.getMessage()).build())
                    .setStatus(ResponseObject
                            .newBuilder()
                            .setCode(200)
                            .setStatus(true)
                    ).build());
        }catch(Exception e){
            responseObserver.onNext(ResponseWrapper
                    .newBuilder()
                    .setStatus(ResponseObject
                            .newBuilder()
                            .setMessage("Error incoming, take cover it from update")
                            .setCode(200)
                            .setStatus(true)).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void saveMessage(SaveRequest request, StreamObserver<ResponseWrapper> responseObserver) {
        try {
            SaveRequestDto existingDto = hazelcastService.save(request);
            //#TODO status return will be fixed for responses and response entity will be fixed
            responseObserver.onNext(ResponseWrapper
                    .newBuilder()
                    .setData(ProtoHelper.fromJson(existingDto.getMessage()).build())
                    .setStatus(ResponseObject
                            .newBuilder()
                            .setCode(200)
                            .setStatus(true)).build());
        } catch (Exception e) {
            responseObserver.onNext(ResponseWrapper
                    .newBuilder()
                    .setStatus(ResponseObject
                            .newBuilder()
                            .setMessage("Error incoming, take cover it from update")
                            .setCode(200)
                            .setStatus(true)).build());
        };
        responseObserver.onCompleted();
    }

    /*

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

 */
}
