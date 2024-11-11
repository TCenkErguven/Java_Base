package org.hazelcast.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND(5200,"Response Bulunamadı", HttpStatus.NOT_FOUND);

    private int code;
    private String message;
    HttpStatus httpStatus;
}