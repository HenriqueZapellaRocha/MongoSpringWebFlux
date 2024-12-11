package com.example.mongospringwebflux.integrationTests.controllers;

import com.example.mongospringwebflux.repository.ProductRepository;
import com.example.mongospringwebflux.repository.entity.ProductEntity;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.ProductResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractBaseIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    ProductRepository productRepository;

    @BeforeAll
    static void setupProducts(@Autowired ProductRepository productRepository) {
        List<ProductEntity> products = Arrays.asList(
                ProductEntity.builder().name("Product 1").description("Description 1")
                        .storeId("Store1").price(new BigDecimal(200)).build(),
                ProductEntity.builder().name("Product 2").description("Description 2")
                        .storeId("Store2").price(new BigDecimal(200)).build()
        );

        productRepository.saveAll(products).blockLast();
    }

    @Test
    void contextLoads() {
        assertThat(mongoDBContainer.isCreated()).isTrue();
        assertThat(mongoDBContainer.isRunning()).isTrue();
    }

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






