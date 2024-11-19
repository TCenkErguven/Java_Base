package com.hazelcast.service;

import com.hazelcast.dto.SaveRequestDto;
import com.hazelcast.server.proto.DuplicateGrpc;
import com.hazelcast.server.proto.SaveRequest;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.concurrent.ExecutorService;

@GrpcService
public class DuplicateGrpcService extends DuplicateGrpc.DuplicateImplBase {

    private final HazelcastService hazelcastService;
    private final ExecutorService executorService;

    public DuplicateGrpcService(HazelcastService hazelcastService,
                                ExecutorService executorService){
        this.hazelcastService = hazelcastService;
        this.executorService = executorService;
    }

    @Override
    public void queryForMessage(SaveRequest request, StreamObserver<SaveRequest> responseObserver) {
        executorService.submit(() -> {
            try {
                hazelcastService.validateRequestUUID(request);
                //#TODO status return will be fixed for responses and response entity will be fixed
                responseObserver.onNext(request);
                responseObserver.onCompleted();
                System.out.println("Processing completed in virtual thread: " + Thread.currentThread());
            } catch (Exception e) {
                responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("Error incoming, take cover it from findUUID")));
            }
        });
    }

    @Override
    public void saveMessage(SaveRequest request, StreamObserver<SaveRequest> responseObserver) {
        executorService.submit(() -> {
            try {
                hazelcastService.update(request);
                //#TODO status return will be fixed for responses and response entity will be fixed
                responseObserver.onNext(request);
                responseObserver.onCompleted();
            } catch (Exception e) {
                responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("Error incoming, take cover it from saveDto")));
            }


        });
    }
}
