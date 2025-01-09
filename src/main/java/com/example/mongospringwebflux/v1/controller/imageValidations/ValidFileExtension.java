package com.example.mongospringwebflux.v1.controller.imageValidations;


import com.example.mongospringwebflux.v1.controller.imageValidations.factory.ExtensionsEnum;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileExtensionValidatorAspect.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFileExtension {
    String message() default "Invalid file extension";

    ExtensionsEnum[] allowedExtensions() default { ExtensionsEnum.PNG, ExtensionsEnum.JPG, ExtensionsEnum.JPEG };

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
