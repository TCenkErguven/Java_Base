package com.hazelcast.service;

import com.hazelcast.server.proto.DuplicateGrpc;
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
    public void queryForMessage(SaveRequest request, StreamObserver<SaveRequest> responseObserver) {
            try {
                hazelcastService.validateRequestUUID(request);
                //#TODO status return will be fixed for responses and response entity will be fixed
                responseObserver.onNext(request);
                responseObserver.onCompleted();
                System.out.println("Processing completed in virtual thread: " + Thread.currentThread());
            } catch (Exception e) {
                responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("Error incoming, take cover it from findUUID")));
            };
    }

    @Override
    public void saveMessage(SaveRequest request, StreamObserver<SaveRequest> responseObserver) {
            try {
                hazelcastService.update(request);
                //#TODO status return will be fixed for responses and response entity will be fixed
                responseObserver.onNext(request);
                responseObserver.onCompleted();
            } catch (Exception e) {
                responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("Error incoming, take cover it from saveDto")));
            };
    }
}
