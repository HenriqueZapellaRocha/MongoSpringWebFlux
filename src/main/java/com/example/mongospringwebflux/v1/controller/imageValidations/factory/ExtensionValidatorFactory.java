package com.example.mongospringwebflux.v1.controller.imageValidations.factory;

import com.example.mongospringwebflux.v1.controller.imageValidations.FileValidator;
import com.example.mongospringwebflux.v1.controller.imageValidations.Jpeg;
import com.example.mongospringwebflux.v1.controller.imageValidations.Jpg;
import com.example.mongospringwebflux.v1.controller.imageValidations.Png;

public class ExtensionValidatorFactory {

    public static FileValidator factory(ExtensionsEnum extension ) {

        switch ( extension ) {

            case JPEG -> {
                return new Jpeg();
            }
            case JPG -> {
                return new Jpg();
            }
            case PNG -> {
                return new Png();
            }
        }

        return null;
    }
}
