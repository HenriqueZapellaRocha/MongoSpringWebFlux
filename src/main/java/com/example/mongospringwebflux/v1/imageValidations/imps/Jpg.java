package com.example.mongospringwebflux.v1.imageValidations.imps;

import com.example.mongospringwebflux.v1.imageValidations.interfaces.FileValidator;

public class Jpg implements FileValidator {

    @Override
    public boolean isValid( String extension ) {
        return extension.equals("jpg");
    }
}
