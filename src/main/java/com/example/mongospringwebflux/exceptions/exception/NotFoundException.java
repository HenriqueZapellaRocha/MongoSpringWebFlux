package com.example.mongospringwebflux.exceptions.exception;





public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
        super(message);
    }
}
