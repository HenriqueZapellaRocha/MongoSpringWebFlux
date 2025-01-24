package com.example.mongospringwebflux.v1.controller.imageValidations.imps;

import com.example.mongospringwebflux.v1.controller.imageValidations.interfaces.FileValidator;

public class Png implements FileValidator {

    @Override
    public boolean isValid(String extension) {
        return extension.equals("png");
    }
}
