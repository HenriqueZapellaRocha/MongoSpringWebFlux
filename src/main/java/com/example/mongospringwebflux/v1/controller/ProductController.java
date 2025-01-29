package com.example.mongospringwebflux.v1.controller;


import com.example.Person;
import com.example.mongospringwebflux.service.facades.ImageLogicFacade;
import com.example.mongospringwebflux.repository.entity.UserEntity;
import com.example.mongospringwebflux.service.services.CookieService;
import com.example.mongospringwebflux.service.services.ProductService;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.ProductRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.ProductResponseDTO;
import com.example.mongospringwebflux.v1.controller.imageValidations.interfaces.ValidFile;
import jakarta.validation.Valid;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping( "/product" )
public class ProductController {

    private final ProductService productService;
    private final ImageLogicFacade imageLogicFacade;


    @PostMapping( "/add" )
    public Mono<ProductResponseDTO> add( @RequestBody @Valid ProductRequestDTO product,
                                         @RequestParam( name = "currency" ) String currency,
                                         @AuthenticationPrincipal UserEntity currentUser ) {

        return productService.add( product,currency, "USD", currentUser );
    }

    @PostMapping( "/uploadProductImage" )
    public Mono<String> uploadFile( @RequestPart( "files" ) @ValidFile FilePart filePart,
                                    @RequestPart( "productId" ) String productId,
                                    @AuthenticationPrincipal UserEntity currentUser ) {

        return imageLogicFacade.validateAndPersistsImage( filePart, productId, currentUser );
    }

//    @GetMapping("/person/{name}/{age}")
//    public Mono<Void> person( @PathVariable String name,
//                              @PathVariable Integer age ) {
//
//        return producerTemplate.send(
//                "services","1", Person.newBuilder()
//                                .setId( "1")
//                                .setName( name )
//                                .setAge( age )
//                        .build()
//        ).doOnNext(result -> System.out.println("Mensagem enviada: " + result.toString()))
//                .then();
//    }


    @GetMapping( "/{id}" )
    public Mono<ProductResponseDTO> getById( @PathVariable String id,
                                             @RequestParam( name = "currency" ) String currency,
                                             ServerHttpResponse response ) {

        return productService.getById( id, "USD", currency )
                .doOnNext( product -> CookieService.setCookie( response, product.productID() ));
    }

    @GetMapping( "/last" )
    public Mono<ProductResponseDTO> getLast( @CookieValue("last") String cookie,
                                             @RequestParam( name = "currency" ) String currency ) {

        return productService.getById( cookie, "USD", currency );
    }

    @GetMapping( "/All" )
    public Flux<ProductResponseDTO> getAll( @RequestParam( name = "currency" ) String currency ) {
        return productService.getAll( "USD", currency );
    }

    @GetMapping( "/storeRelated/{id}" )
    public Flux<ProductResponseDTO> allProductStoreRelated( @RequestParam( name = "currency" ) String currency,
                                                            @PathVariable String id ) {
        return productService.getAllProductsRelatedStore( id, currency );
    }

    @PutMapping( "/{id}" )
    public Mono<ProductResponseDTO> update( @RequestBody @Valid ProductRequestDTO product,
                                            @PathVariable String id,
                                            @AuthenticationPrincipal UserEntity currentUser,
                                            @RequestParam( name = "currency" ) String currency ) {

        return productService.update( product, id, currentUser.getStoreId(), currency, "USD" );
    }

    @DeleteMapping( "/{id}" )
    public Mono<Void> deleteById( @PathVariable String id,
                                  @AuthenticationPrincipal UserEntity currentUser ) {
        return productService.deleteMany( List.of(id), currentUser.getStoreId() );
    }

    @DeleteMapping
    public Mono<Void> deleteMany( @RequestBody List<String> id,
                                  @AuthenticationPrincipal UserEntity currentUser ) {
        return productService.deleteMany( id, currentUser.getStoreId() );
    }
}
