package com.example.mongospringwebflux.exceptions.exception;


import lombok.Getter;

@Getter
public class GlobalException extends Exception {

    public GlobalException(String message) {
        super(message);
    }
}
