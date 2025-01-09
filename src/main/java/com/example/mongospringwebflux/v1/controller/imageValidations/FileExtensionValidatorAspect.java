package com.example.mongospringwebflux.v1.controller.imageValidations;

import com.example.mongospringwebflux.exception.GlobalException;
import com.example.mongospringwebflux.v1.controller.imageValidations.factory.ExtensionValidatorFactory;
import com.example.mongospringwebflux.v1.controller.imageValidations.factory.ExtensionsEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.compress.utils.FileNameUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Aspect
@Component
public class FileExtensionValidatorAspect implements ConstraintValidator<ValidFileExtension, FilePart> {


    private ExtensionsEnum[] allowedExtensions;

    @Override
    public void initialize( ValidFileExtension constraintAnnotation ) {
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
                .filter( Objects::nonNull )
                .anyMatch( extensionValidator -> extensionValidator.isValid( extension ) );
    }
}
