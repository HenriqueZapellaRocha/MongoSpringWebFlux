package com.example.mongospringwebflux;

import com.example.mongospringwebflux.dtos.NotFoundExceptionDTO;
import com.example.mongospringwebflux.integrationTests.controllers.basicModels.AbstractBaseIntegrationTest;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.ProductRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.ProductResponseDTO;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
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
    public void test_GetAllProducts_AuthorizedUser_() {

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

    // GetbyId tests

    @Test
    public void test_GetByIdProduct_UnauthorizedUser_returnUnauthorized() {

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+PRODUCT_1.getProductID())
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " +
                        RandomStringUtils.randomAlphanumeric(30))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void test_GetByIdProduct_AuthorizedUser_returnOk() {
        ProductResponseDTO PRODUCT_REQUEST_1 = ProductResponseDTO.entityToResponse(PRODUCT_1, "USD");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+PRODUCT_1.getProductID())
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
                });

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+PRODUCT_1.getProductID())
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
                });

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+PRODUCT_1.getProductID())
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
                });
    }

    @Test
    public void test_GetByIdProduct_AuthorizedUser_returnUnauthorized() {

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+PRODUCT_1.getProductID())
                        .build())
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void test_GetByIdProduct_AuthorizedUser_ReturnNotFound() {

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+PRODUCT_1.getProductID())
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

    @Test
    public void test_GetByIdProduct_dontExistTheProduct_AuthorizedUser_ReturnNotFound() {

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+"123")
                        .queryParam("currency", "--")
                        .build())
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBodyList( NotFoundExceptionDTO.class )
                .consumeWith( response -> {
                    List<NotFoundExceptionDTO> notFound = response.getResponseBody();

                    assert notFound != null;
                    assert notFound.get( 0 ).getError().equals( "No product found" );
                });
    }

    // ADD PRODUCTS TESTS

    @Test
    public void test_addProduct_AuthorizedUser_ReturnOkAndObjectCreated() {

        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("NEW PRODUCT CREATED")
                .price( new BigDecimal(200) )
                .description("TEST")
                .build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path( "/product/add" )
                        .queryParam( "currency", "EUR" )
                        .build())
                .header( "Authorization", "Bearer " + STORE_ADMIN_TOKEN )
                .bodyValue(productRequestDTO)
                .accept( MediaType.APPLICATION_JSON )
                .exchange()
                .expectStatus().isOk()
                .expectBodyList( ProductResponseDTO.class )
                .consumeWith( response -> {
                    List<ProductResponseDTO> products = response.getResponseBody();

                    System.out.println(products.get(0));

                    assert products.get( 0 ).name().equals( "NEW PRODUCT CREATED" );
                    assert products.get( 0 ).price().currency().equals( "USD" );
                    assert products.get( 0 ).description().equals( "TEST" );
                    assert products.get( 0 ).store().equals( USER_STORE_ADMIN.getStoreId() );

                });
    }

    @Test
    public void test_addProduct_UnauthorizedUser_ReturnUnauthorized() {

        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("NEW PRODUCT CREATED")
                .price( new BigDecimal(200) )
                .description("TEST")
                .build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path( "/product/add" )
                        .queryParam( "currency", "EUR" )
                        .build())
                .header( "Authorization", "Bearer " + USER_TOKEN )
                .bodyValue(productRequestDTO)
                .accept( MediaType.APPLICATION_JSON )
                .exchange()
                .expectStatus().isUnauthorized();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path( "/product/add" )
                        .queryParam( "currency", "EUR" )
                        .build())
                .header( "Authorization", "Bearer " + ADMIN_TOKEN )
                .bodyValue(productRequestDTO)
                .accept( MediaType.APPLICATION_JSON )
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void test_addProduct_AuthorizedUser_CurrencyNotFound() {

        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("NEW PRODUCT CREATED")
                .price( new BigDecimal(200) )
                .description("TEST")
                .build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path( "/product/add" )
                        .queryParam( "currency", "--" )
                        .build())
                .header( "Authorization", "Bearer " + STORE_ADMIN_TOKEN )
                .bodyValue(productRequestDTO)
                .accept( MediaType.APPLICATION_JSON )
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

