package com.example.mongospringwebflux.v1.imageValidations.factory;


import com.example.mongospringwebflux.v1.imageValidations.imps.Jpeg;
import com.example.mongospringwebflux.v1.imageValidations.imps.Jpg;
import com.example.mongospringwebflux.v1.imageValidations.imps.Png;
import com.example.mongospringwebflux.v1.imageValidations.interfaces.FileValidator;


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
            default -> throw new RuntimeException( "This strategie is not recognized" );
        }
    }
}
