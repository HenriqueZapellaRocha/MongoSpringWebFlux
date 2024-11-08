package com.example.mongospringwebflux.handler;


import com.example.mongospringwebflux.dtos.CookieNotSetExceptionDTO;
import com.example.mongospringwebflux.dtos.GlobalExceptionDTO;
import com.example.mongospringwebflux.dtos.InvalidInputValuesExceptionDTO;
import com.example.mongospringwebflux.dtos.NotFoundExceptionDTO;
import com.example.mongospringwebflux.exception.NotFoundException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MissingRequestValueException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionsHandlers {

    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ResponseBody
    @ExceptionHandler( ResponseStatusException.class )
    public Mono<NotFoundExceptionDTO> handleNoHandlerFoundException( final ResponseStatusException e ) {
        return Mono.just( new NotFoundExceptionDTO( e.getMessage() ) );
    }

    @ResponseStatus( HttpStatus.NOT_FOUND )
    @ResponseBody
    @ExceptionHandler( HttpClientErrorException.class )
    public Mono<NotFoundExceptionDTO> handler( final HttpClientErrorException e ) {
        return Mono.just( new NotFoundExceptionDTO( "currency not found" ) );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MissingRequestValueException.class)
    public Mono<CookieNotSetExceptionDTO> handler(final MissingRequestValueException e) {
        return Mono.just(new CookieNotSetExceptionDTO("cookie not set"));
    }

    @ResponseStatus( HttpStatus.NOT_FOUND )
    @ResponseBody
    @ExceptionHandler( NotFoundException.class )
    public Mono<NotFoundExceptionDTO> handler(final NotFoundException e ) {
        return Mono.just( new  NotFoundExceptionDTO( e.getMessage() ));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<InvalidInputValuesExceptionDTO> handleValidationException(WebExchangeBindException ex) {
        List<String> errorMessages = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        InvalidInputValuesExceptionDTO errorResponse = new InvalidInputValuesExceptionDTO(errorMessages);
        return Mono.just(errorResponse);
    }

//    @ResponseStatus( HttpStatus.BAD_REQUEST )
//    @ExceptionHandler(ErrorWebExceptionHandler.class)
//    public GlobalExceptionDTO handleGlobalException(Exception ex, WebRequest request) {
//        return new GlobalExceptionDTO( "An unknown error occurred. Please consult the support for resolution." );
//    }
}