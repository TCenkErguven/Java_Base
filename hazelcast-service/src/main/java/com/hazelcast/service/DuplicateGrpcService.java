package com.hazelcast.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.util.JsonFormat;
import com.hazelcast.model.Custom;
import com.hazelcast.server.proto.*;
import com.hazelcast.utility.ProtoHelper;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Map;

@GrpcService
public class DuplicateGrpcService extends DuplicateGrpc.DuplicateImplBase {

    private final HazelcastService hazelcastService;
    private final ObjectMapper objectmapper;

    public DuplicateGrpcService(HazelcastService hazelcastService){
        this.hazelcastService = hazelcastService;
        this.objectmapper = new ObjectMapper();
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
            //#TODO JSONFORMAT dependency will be added
            String jsonCustom = objectmapper.writeValueAsString(custom);
            Struct.Builder structBuilder = Struct.newBuilder();
            JsonFormat.parser().ignoringUnknownFields().merge(jsonCustom, structBuilder);
            Struct struct = structBuilder.build();
            responseObserver.onNext(ResponseWrapper
                    .newBuilder()
                    .setData(ProtoHelper.fromJson(jsonCustom).build())
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
