package com.example.mongospringwebflux.exception;

import lombok.Getter;

@Getter
public class InvalidInputValuesException extends RuntimeException {

    public InvalidInputValuesException(String message) {
        super(message);
    }
}