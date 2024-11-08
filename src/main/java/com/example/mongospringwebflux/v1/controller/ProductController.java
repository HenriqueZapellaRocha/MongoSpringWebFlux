package com.example.mongospringwebflux.v1.controller;

import com.example.mongospringwebflux.service.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@RestController
@RequestMapping( "/product" )
public class ProductController {

    private final ProductService productService;


    @PostMapping( "/add" )
    public Mono<ProductResponseDTO> add(@RequestBody @Valid ProductRequestDTO product,
                                        @RequestParam( name = "currency" ) String currency ) {
        return productService.add( product,currency, "USD" );
    }

    @GetMapping( "/{id}" )
    public Mono<ProductResponseDTO> getById( @PathVariable String id,
                                        @RequestParam( name = "currency" ) String currency ) {
        return productService.getById( id,"USD", currency );
    }

//    @GetMapping( "/last" )
//    public ProductResponseDTO getLast( HttpServletRequest request,
//                                 @RequestParam( name = "currency" ) String currency ) {
//        final Cookie cookie = CookieService.getCookie( request, "last" );
//
//        return productService.getById( cookie.getValue(),"USD", currency );
//    }

    @GetMapping( "/All" )
    public Flux<ProductResponseDTO> getAll( @RequestParam( name = "currency" ) String currency ) {
        return productService.getAll( "USD", currency );
    }

    @PutMapping( "/{id}" )
    public Mono<ProductResponseDTO> update( @RequestBody @Valid ProductRequestDTO product, @PathVariable String id ) {
        return productService.update( product, id );
    }

//    @DeleteMapping( "/{id}" )
//    public void deleteById( @PathVariable String id ) {
//        final LinkedList<String> ids = new LinkedList<>();
//        ids.add( id );
//        productService.deleteMany( ids );
//    }
//

//    @DeleteMapping
//    public void deleteMany( @RequestBody List<String> id ) {
//        productService.deleteMany( id );
//    }
}
