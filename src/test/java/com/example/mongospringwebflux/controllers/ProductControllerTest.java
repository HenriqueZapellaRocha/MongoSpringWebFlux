package com.example.mongospringwebflux.controllers;

import com.example.mongospringwebflux.MockBuiilders.MockBuilder;
import com.example.mongospringwebflux.exception.NotFoundException;
import com.example.mongospringwebflux.service.services.ProductService;
import com.example.mongospringwebflux.v1.controller.ProductController;
import com.example.mongospringwebflux.v1.controller.ProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@WebFluxTest(controllers = ProductController.class)
@AutoConfigureWebTestClient
class ProductControllerTest {


    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ProductService productService;

    @Test
    void productControllerTestAddProductReturnOk() {

        var productRequest = MockBuilder.productRequest();
        var productResponse = ProductResponseDTO
                .entityToResponse( productRequest.toEntity( "DDD" ), "BRL" );

        when( productService.add(any(), anyString(), any()) )
                .thenReturn(Mono.just( productResponse ));

        WebTestClient.ResponseSpec response = webClient.post()
                .uri(uriBuilder -> uriBuilder.path( "/product/add" )
                        .queryParam( "currency", "BRL" )
                        .build())
                .contentType( MediaType.APPLICATION_JSON )
                .bodyValue( productRequest )
                .exchange();


        response.expectStatus().isOk()
                .expectBody()
                .jsonPath( "$.productID" ).isEqualTo( productResponse.productID() )
                .jsonPath( "$.name" ).isEqualTo( productResponse.name() )
                .jsonPath( "$.price.value" ).isEqualTo( productResponse.price().value().doubleValue() )
                .jsonPath( "$.price.currency" ).isEqualTo( productResponse.price().currency() );
    }

    @Test
    void productControllerTestAddCurrencyNotFoundReturnNotFound() {

        var productRequest = MockBuilder.productRequest();
        var productResponse = ProductResponseDTO
                .entityToResponse( productRequest.toEntity( "DDD" ), "ZZZ" );

        when(productService.add(any(),any(),any()))
                .thenThrow( new NotFoundException( "currency not found" ) );

        WebTestClient.ResponseSpec response = webClient.post()
                .uri(uriBuilder -> uriBuilder.path( "/product/add" )
                        .queryParam( "currency", "BRL" )
                        .build())
                .contentType( MediaType.APPLICATION_JSON )
                .bodyValue( productRequest )
                .exchange();


        response.expectStatus().isNotFound()
                .expectBody()
                .jsonPath( "$.error" ).isEqualTo(  "currency not found" );
    }

    @Test
    void productControllerTestAddInvalidProductReturnErrros() {

        var productRequest = MockBuilder.productRequestInvalidValues();

        WebTestClient.ResponseSpec response = webClient.post()
                .uri( uriBuilder -> uriBuilder.path( "/product/add" )
                        .queryParam( "currency", "BRL" )
                        .build())
                .contentType( MediaType.APPLICATION_JSON )
                .bodyValue( productRequest )
                .exchange();
        
        response.expectStatus().isBadRequest()
                .expectBody()
                .jsonPath( "$.errors[0]" ).isEqualTo(  "name: blank name" )
                .jsonPath( "$.errors[1]" ).isEqualTo(  "price: negative number" );
    }


    @Test
    void productControllerTestGetByIdProductReturnOk() {

        var  productResponse = MockBuilder.productResponse();
        when(productService.getById( anyString(),anyString(),anyString() ) )
                .thenReturn( Mono.just( productResponse ) );


        WebTestClient.ResponseSpec response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+productResponse.productID())
                        .queryParam("currency", "BRL")
                        .build())
                .exchange();


        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.productID").isEqualTo(productResponse.productID())
                .jsonPath("$.price.value").isEqualTo(productResponse.price().value().doubleValue())
                .jsonPath("$.price.currency").isEqualTo(productResponse.price().currency())
                .jsonPath( "$.name" ).isEqualTo(productResponse.name());
    }

    @Test
    void productControllerTestGetByIdProductDontExistReturnNotFound() {

        var productResponse = MockBuilder.productResponse();

        when( productService.getById( any(), any(), any()) )
                .thenThrow( new NotFoundException( "No product found" ) );


        WebTestClient.ResponseSpec response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+productResponse.productID())
                        .queryParam("currency", "BRL")
                        .build())
                .exchange();


        response.expectStatus().isNotFound()
                .expectBody()
                .consumeWith( System.out::println )
                .jsonPath( "$.error" ).isEqualTo( "No product found" );
    }

    @Test
    void productControllerTestGetByIdCurrencyNotFound() {

        var productResponse = MockBuilder.productResponse();

        when( productService.getById( any(), any(), any()) )
                .thenThrow( new NotFoundException( "currency not found" ) );


        WebTestClient.ResponseSpec response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path( "/product/" + productResponse.productID() )
                        .queryParam( "currency", " =) " )
                        .build())
                .exchange();


        response.expectStatus().isNotFound()
                .expectBody()
                .consumeWith( System.out::println )
                .jsonPath( "$.error" ).isEqualTo( "currency not found" );
    }
}


