package com.grpc.server.exception.grpc;

import io.grpc.Status;
import io.grpc.StatusException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;


@GrpcAdvice
public class GrpcGlobalExceptionHandler {

    /**
     * @GrpcAdvice needed to be used for us to be able to use
     * @GrpcExceptionHandler which can catch the related grpc errors,
     * those stated as function parameters
     * @param e
     * @return
     */
    @GrpcExceptionHandler
    public StatusException handleIllegalArgumentException(IllegalArgumentException e){
        return Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e).asException();
    }
}
