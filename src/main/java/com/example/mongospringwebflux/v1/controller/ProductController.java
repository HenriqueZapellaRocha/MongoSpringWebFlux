package com.example.mongospringwebflux.v1.controller;

import com.example.mongospringwebflux.repository.entity.UserEntity;
import com.example.mongospringwebflux.service.services.CookieService;
import com.example.mongospringwebflux.service.services.ProductService;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.ProductRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.ProductResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.LinkedList;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping( "/product" )
public class ProductController {

    private final ProductService productService;


    @PostMapping( "/add" )
    public Mono<ProductResponseDTO> add( @RequestBody @Valid ProductRequestDTO product,
                                        @RequestParam( name = "currency" ) String currency,
                                        @AuthenticationPrincipal UserEntity currentUser ) {

        return productService.add( product,currency, "USD", currentUser );
    }

    @GetMapping( "/{id}" )
    public Mono<ProductResponseDTO> getById( @PathVariable String id,
                                        @RequestParam( name = "currency" ) String currency,
                                             ServerHttpResponse response ) {

        return productService.getById( id, "USD", currency )
                .doOnNext( product -> {
                    CookieService.setCookie( response, product.productID() );
                });
    }

    @GetMapping( "/last" )
    public Mono<ProductResponseDTO> getLast( @CookieValue("last") String cookie,
                                 @RequestParam( name = "currency" ) String currency ) {
        return productService.getById (cookie, "USD", currency );
    }

    @GetMapping( "/All" )
    public Flux<ProductResponseDTO> getAll( @RequestParam( name = "currency" ) String currency ) {
        return productService.getAll( "USD", currency );
    }

    @PutMapping( "/{id}" )
    public Mono<ProductResponseDTO> update( @RequestBody @Valid ProductRequestDTO product, @PathVariable String id ) {
        return productService.update( product, id );
    }

    @DeleteMapping( "/{id}" )
    public Mono<Void> deleteById( @PathVariable String id ) {
        final LinkedList<String> ids = new LinkedList<>();
        ids.add( id );
        return productService.deleteMany( ids );
    }

    @DeleteMapping
    public Mono<Void> deleteMany( @RequestBody List<String> id ) {
        return productService.deleteMany( id );
    }
}
