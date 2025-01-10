package com.example.mongospringwebflux.v1.controller.imageValidations;

public class Jpeg implements FileValidator {
    @Override
    public boolean isValid( String extension ) {
        return extension.equals("jpeg");
    }
}
