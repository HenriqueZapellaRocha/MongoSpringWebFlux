package com.example.mongospringwebflux.controllers;

import com.example.mongospringwebflux.MockBuiilders.MockBuilder;
import com.example.mongospringwebflux.exception.NotFoundException;
import com.example.mongospringwebflux.service.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureWebTestClient
class ProductControllerTest {


    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ProductService productService;


    @Test
    void productControllerTestGetByIdProductReturnOk() {

        var  productResponse = MockBuilder.productResponse();
        when(productService.getById(anyString(),anyString(),anyString())).thenReturn(Mono.just(productResponse));


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
                .jsonPath("$.name").isEqualTo(productResponse.name());
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


}


