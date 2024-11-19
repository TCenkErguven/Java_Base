package com.hazelcast.exception;


public abstract class HazelCastBaseException extends RuntimeException {
    private final ErrorType errorType;

    public HazelCastBaseException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public HazelCastBaseException(ErrorType errorType) {
        super();
        this.errorType = errorType;
    }
}
