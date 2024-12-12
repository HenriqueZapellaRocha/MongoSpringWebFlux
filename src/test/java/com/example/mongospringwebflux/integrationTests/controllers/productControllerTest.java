package com.example.mongospringwebflux.integrationTests.controllers;

import com.example.mongospringwebflux.integrationTests.controllers.basicModels.AbstractBaseIntegrationTest;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.ProductResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

public class productControllerTest extends AbstractBaseIntegrationTest {


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllProductsWithCurrency() {

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .queryParam("currency", "EUR")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .consumeWith(response -> {
                    System.out.println("Resposta do corpo: " + response.getResponseBody());
                });
    }
}
