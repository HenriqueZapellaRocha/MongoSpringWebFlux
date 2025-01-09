package com.example.mongospringwebflux.v1.controller.imageValidations;

public class Png implements ExtensionValidator {

    @Override
    public boolean isValid(String extension) {
        return extension.equals("png");
    }
}
