package com.example.mongospringwebflux.handler;


import com.example.mongospringwebflux.dtos.NotFoundExceptionDTO;
import com.example.mongospringwebflux.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import reactor.core.publisher.Mono;


@RestControllerAdvice
public class GlobalExceptionsHandlers {

//    @ResponseStatus( HttpStatus.BAD_REQUEST )
//    @ResponseBody
//    @ExceptionHandler( NoHandlerFoundException.class )
//    public NotFoundExceptionDTO handleNoHandlerFoundException( final NoHandlerFoundException e ) {
//        return new NotFoundExceptionDTO( e.getMessage() );
//    }
//
//    @ResponseStatus( HttpStatus.NOT_FOUND )
//    @ResponseBody
//    @ExceptionHandler( HttpClientErrorException.class )
//    public NotFoundExceptionDTO handler( final HttpClientErrorException e ) {
//
//        return new NotFoundExceptionDTO( "currency not found" );
//    }
//
//
//    @ResponseStatus( HttpStatus.BAD_REQUEST )
//    @ResponseBody
//    @ExceptionHandler( CookieNotSetException.class )
//    public CookieNotSetExceptionDTO handler( final CookieNotSetException e ) {
//        return new CookieNotSetExceptionDTO( e.getMessage() );
//    }
//
    @ResponseStatus( HttpStatus.NOT_FOUND )
    @ResponseBody
    @ExceptionHandler( NotFoundException.class )
    public Mono<NotFoundExceptionDTO> handler(final NotFoundException e ) {
        return Mono.just( new  NotFoundExceptionDTO( e.getMessage() ));
    }
//
//    @ResponseStatus( HttpStatus.BAD_REQUEST )
//    @ResponseBody
//    @ExceptionHandler( MethodArgumentNotValidException.class )
//    public InvalidInputValuesExceptionDTO handle( MethodArgumentNotValidException ex ) {
//        return new InvalidInputValuesExceptionDTO( ex.getBindingResult()
//        .getFieldErrors()
//        .stream()
//        .map( FieldError::getDefaultMessage )
//        .toList() );
//        }
//
//    @ResponseStatus( HttpStatus.BAD_REQUEST )
//    @ExceptionHandler(Exception.class)
//    public GlobalExceptionDTO handleGlobalException(Exception ex, WebRequest request) {
//        return new GlobalExceptionDTO( "An unknown error occurred. Please consult the support for resolution." );
//    }
}