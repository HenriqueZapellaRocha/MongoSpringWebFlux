package com.example.mongospringwebflux.exceptions.exception;

import lombok.Getter;

@Getter
public class CookieNotSetException extends RuntimeException {

    public CookieNotSetException(String message) {
        super(message);
    }
}
