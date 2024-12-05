package com.example.mongospringwebflux.controllers;

import com.example.mongospringwebflux.MockBuiilders.MockBuilder;
import com.example.mongospringwebflux.service.services.ProductService;
import com.example.mongospringwebflux.v1.controller.ProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void productControllerTestAddProductReturnOk() {
        // Mock da resposta do serviço
        var  productResponse = MockBuilder.productResponse();
        when(productService.getById(anyString(),anyString(),anyString())).thenReturn(Mono.just(productResponse));

        // Execução do teste
        WebTestClient.ResponseSpec response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+productResponse.productID())
                        .queryParam("currency", "BRL")
                        .build())
                .exchange();

        // Validação do status da resposta
        response.expectStatus().isOk();
    }


}
