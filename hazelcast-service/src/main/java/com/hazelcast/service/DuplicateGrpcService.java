package com.hazelcast.service;

import com.hazelcast.model.Custom;
import com.hazelcast.server.proto.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class DuplicateGrpcService extends DuplicateGrpc.DuplicateImplBase {

    private final HazelcastService hazelcastService;

    public DuplicateGrpcService(HazelcastService hazelcastService){
        this.hazelcastService = hazelcastService;
    }

    @Override
    public void saveMessage(SaveRequest request, StreamObserver<ResponseWrapper> responseObserver) {
        try {
            hazelcastService.update(request);
            //#TODO status return will be fixed for responses and response entity will be fixed
            responseObserver.onNext(ResponseWrapper
                    .newBuilder()
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

    @Override
    public void queryForMessage(FindUUIDRequest request, StreamObserver<ResponseWrapper> responseObserver) {
        try {
            Custom custom = hazelcastService.validateRequestUUID(request);
            Struct.Builder structBuilder = Struct.newBuilder();
            //#TODO JSONFORMAT dependency will be added
            // JsonFormat.parser().ignoringUnknownFields().merge(json, structBuilder);


            responseObserver.onNext(ResponseWrapper
                    .newBuilder()
                    .setCustom(CustomResponse
                            .newBuilder()
                            .setUuid(custom.getTransactionUUID())
                            .setTransactionMessage(custom.getMessage().toString())
                            .build())
                    .setStatus(ResponseObject
                            .newBuilder()
                            .setCode(200)
                            .setStatus(true)
                            ).build());
        } catch (Exception e) {
            responseObserver.onNext(ResponseWrapper
                    .newBuilder()
                    .setStatus(ResponseObject
                            .newBuilder()
                            .setMessage(e.getMessage())
                            .setCode(200)
                            .setStatus(true)).build());
        };
        responseObserver.onCompleted();
    }

}
