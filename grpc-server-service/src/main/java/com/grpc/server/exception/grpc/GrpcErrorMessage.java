package com.grpc.server.exception.grpc;

import com.google.rpc.ErrorInfo;
import io.grpc.Metadata;
import io.grpc.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GrpcErrorMessage {
    private Status status;
    private ErrorInfo errorInfo;
    private Metadata metadata;
}
