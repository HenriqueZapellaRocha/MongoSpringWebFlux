package com.example.mongospringwebflux.v1.imageValidations.imps;


import com.example.mongospringwebflux.v1.imageValidations.factory.ExtensionValidatorFactory;
import com.example.mongospringwebflux.v1.imageValidations.factory.ExtensionsEnum;
import com.example.mongospringwebflux.v1.imageValidations.interfaces.ValidFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.http.codec.multipart.FilePart;
import java.util.Arrays;



public class FileExtensionValidatorAspect implements ConstraintValidator<ValidFile, FilePart> {


    private ExtensionsEnum[] allowedExtensions;

    @Override
    public void initialize( ValidFile constraintAnnotation ) {
        this.allowedExtensions = constraintAnnotation.allowedExtensions();
    }

    @Override
    public boolean isValid( FilePart filePart, ConstraintValidatorContext context ) {

        if (filePart == null) {
            return true;
        }

        String extension = FileNameUtils.getExtension( filePart.filename() );

        return Arrays.stream(allowedExtensions)
                .map( ExtensionValidatorFactory::factory )
                .anyMatch( extensionValidator -> extensionValidator.isValid( extension ) );
    }
}
