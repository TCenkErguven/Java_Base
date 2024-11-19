package com.hazelcast.exception;

import lombok.Getter;

@Getter
public class HazelCastServiceSaveException extends HazelCastBaseException{


    public HazelCastServiceSaveException(String message, ErrorType errorType) {
        super(message, errorType);
    }

    public HazelCastServiceSaveException(ErrorType errorType) {
        super(errorType);
    }
}
