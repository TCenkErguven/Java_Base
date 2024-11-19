package com.hazelcast.exception;

import lombok.Getter;

@Getter
public class HazelCastServiceUpdateException extends HazelCastBaseException {
    public HazelCastServiceUpdateException(String message, ErrorType errorType) {
        super(message, errorType);
    }
    public HazelCastServiceUpdateException(ErrorType errorType) {
        super(errorType);
    }
}
