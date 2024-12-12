package com.example.mongospringwebflux;

import com.example.mongospringwebflux.dtos.NotFoundExceptionDTO;
import com.example.mongospringwebflux.integrationTests.controllers.basicModels.AbstractBaseIntegrationTest;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.ProductResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;


public class productControllerTest extends AbstractBaseIntegrationTest {





    @Test
    public void test_getAllProducts_UnauthorizedUser_returnUnauthorized() {

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " +
                        RandomStringUtils.randomAlphanumeric(30))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @DisplayName( "Test get all products for all authorized rules( admin, soter admin and normal user ) " +
            "and return ok with correct body" )
    public void test_GetAllProducts_AuthorizedUser_returnOk() {
        ProductResponseDTO PRODUCT_REQUEST_1 = ProductResponseDTO.entityToResponse(PRODUCT_1, "USD");
        ProductResponseDTO PRODUCT_REQUEST_2 = ProductResponseDTO.entityToResponse(PRODUCT_2, "USD");
        ProductResponseDTO PRODUCT_REQUEST_3 = ProductResponseDTO.entityToResponse(PRODUCT_3, "USD");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .consumeWith(response -> {
                    List<ProductResponseDTO> products = response.getResponseBody();

                    assert products != null;
                    assert products.contains( PRODUCT_REQUEST_1 );
                    assert products.contains( PRODUCT_REQUEST_2 );
                    assert products.contains( PRODUCT_REQUEST_3 );


                });

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " + USER_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .consumeWith(response -> {
                    List<ProductResponseDTO> products = response.getResponseBody();

                    assert products != null;
                    assert products.contains( PRODUCT_REQUEST_1 );
                    assert products.contains( PRODUCT_REQUEST_2 );
                    assert products.contains( PRODUCT_REQUEST_3 );


                });

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " + STORE_ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .consumeWith(response -> {
                    List<ProductResponseDTO> products = response.getResponseBody();

                    assert products != null;
                    assert products.contains( PRODUCT_REQUEST_1 );
                    assert products.contains( PRODUCT_REQUEST_2 );
                    assert products.contains( PRODUCT_REQUEST_3 );
                });
    }

    @Test
    @DisplayName( "Test when the currency query param not send" )
    public void test_GetAllProducts_AuthorizedUser_returnUnauthorized() {

        ProductResponseDTO PRODUCT_REQUEST_1 = ProductResponseDTO.entityToResponse(PRODUCT_1, "USD");
        ProductResponseDTO PRODUCT_REQUEST_2 = ProductResponseDTO.entityToResponse(PRODUCT_2, "USD");
        ProductResponseDTO PRODUCT_REQUEST_3 = ProductResponseDTO.entityToResponse(PRODUCT_3, "USD");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .build())
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @DisplayName( "Test when the currency now exist" )
    public void test_GetAllProducts_AuthorizedUser_() {

        Mono<String> user = tokenService.validateToke(ADMIN_TOKEN);
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .queryParam("currency", "UZ")
                        .build())
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBodyList( NotFoundExceptionDTO.class )
                .consumeWith( response -> {
                    List<NotFoundExceptionDTO> notFound = response.getResponseBody();

                    assert notFound != null;
                    assert notFound.get( 0 ).getError().equals( "Currency not found" );
                });
    }

}

