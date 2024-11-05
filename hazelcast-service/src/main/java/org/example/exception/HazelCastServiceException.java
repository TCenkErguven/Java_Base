package org.example.exception;

import lombok.Getter;

@Getter
public class HazelCastServiceException extends RuntimeException{

    private final ErrorType errorType;

    public HazelCastServiceException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }

    public HazelCastServiceException(ErrorType errorType){
        this.errorType = errorType;
    }

}
