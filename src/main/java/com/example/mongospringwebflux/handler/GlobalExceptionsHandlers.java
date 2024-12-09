package com.example.mongospringwebflux.handler;


import com.example.mongospringwebflux.dtos.GlobalExceptionDTO;
import com.example.mongospringwebflux.dtos.InvalidInputValuesExceptionDTO;
import com.example.mongospringwebflux.dtos.NotFoundExceptionDTO;
import com.example.mongospringwebflux.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionsHandlers {

    @ResponseStatus( HttpStatus.NOT_FOUND )
    @ResponseBody
    @ExceptionHandler( NotFoundException.class )
    public Mono<NotFoundExceptionDTO> handler(final NotFoundException e ) {
        return Mono.just( new  NotFoundExceptionDTO( e.getMessage() ));
    }

    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( WebExchangeBindException.class )
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
//    @ExceptionHandler( ResponseStatusException.class )
//    public Mono<GlobalExceptionDTO> handleGlobalException(ResponseStatusException ex) {
//        return Mono.just(
//                new GlobalExceptionDTO( "An unknown error occurred. Please consult the support for resolution." ));
//    }
}