package com.weatherApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidDataException extends IllegalArgumentException {

    public InvalidDataException(String message) {
        super(message);
    }
}
