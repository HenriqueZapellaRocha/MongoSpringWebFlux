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
//
//    @Operation( description = "Get the last product consulted in get by id using cookie",
//            responses = {@ApiResponse(responseCode = "400", description = "No cookie is set",
//                    content = @Content(schema = @Schema(implementation = CookieNotSetExceptionDTO.class ))) ,
//                        @ApiResponse( responseCode = "404", description = "No product found",
//                    content = @Content( schema = @Schema(implementation = NotFoundExceptionDTO.class ))) })
//    @GetMapping( "/last" )
//    public ProductResponseDTO getLast( HttpServletRequest request,
//                                 @RequestParam( name = "currency" ) String currency ) {
//        final Cookie cookie = CookieService.getCookie( request, "last" );
//
//        return productService.getById( cookie.getValue(),"USD", currency );
//    }
//
    @GetMapping( "/All" )
    public Flux<ProductResponseDTO> getAll( @RequestParam( name = "currency" ) String currency ) {
        return productService.getAll( "USD", currency );
    }
//
//    @ApiResponse(responseCode = "200")})
//    @PutMapping( "/{id}" )
//    public ProductResponseDTO update( @RequestBody @Valid ProductRequestDTO product, @PathVariable String id ) {
//        return productService.update( product, id );
//    }
//
//    @Operation( description = "Delete a product in api by the id",
//            responses = {@ApiResponse( description = "OK", responseCode = "200") })
//    @DeleteMapping( "/{id}" )
//    public void deleteById( @PathVariable String id ) {
//        final LinkedList<String> ids = new LinkedList<>();
//        ids.add( id );
//        productService.deleteMany( ids );
//    }
//
//    @Operation(
//            description = "Deletes many products by a list of ids",
//            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody( content = @Content( schema = @Schema(
//                                    implementation = ProductEntity[].class
//                            ), examples = { @ExampleObject(
//                                            name = "API request body example:",
//                                            value = "[\n\"6ec58140-b159-4a5b-af91-3f976f8ebcb4\",\n" +
//                                                    "\"34efad68-cb0b-47d3-a204-a677532f0ecc\",\n" +
//                                                    "\"b7cc702c-97f5-419f-858e-17acf7f45d13\"\n\n]"
//                                    )})))
//    @DeleteMapping
//    public void deleteMany( @RequestBody List<String> id ) {
//        productService.deleteMany( id );
//    }
}
