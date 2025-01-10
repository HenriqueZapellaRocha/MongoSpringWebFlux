package com.example.mongospringwebflux.v1.controller.imageValidations.factory;

import com.example.mongospringwebflux.exception.GlobalException;
import com.example.mongospringwebflux.v1.controller.imageValidations.FileValidator;
import com.example.mongospringwebflux.v1.controller.imageValidations.Jpeg;
import com.example.mongospringwebflux.v1.controller.imageValidations.Jpg;
import com.example.mongospringwebflux.v1.controller.imageValidations.Png;
import reactor.core.publisher.Mono;

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
            default ->  {
                Mono.error( new GlobalException( "Internal server error"  ) ).then();
            }
        }

        return null;
    }
}
