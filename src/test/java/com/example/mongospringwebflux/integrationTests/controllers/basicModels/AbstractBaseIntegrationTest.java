package com.example.mongospringwebflux.integrationTests.controllers.basicModels;

import com.example.mongospringwebflux.repository.ProductRepository;
import com.example.mongospringwebflux.repository.StoreRepository;
import com.example.mongospringwebflux.repository.UserRepository;
import com.example.mongospringwebflux.repository.entity.ProductEntity;
import com.example.mongospringwebflux.repository.entity.StoreEntity;
import com.example.mongospringwebflux.repository.entity.UserEntity;
import com.example.mongospringwebflux.repository.entity.enums.UserRoles;
import com.example.mongospringwebflux.service.services.securityServices.TokenService;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.ProductResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@SpringBootTest
public class AbstractBaseIntegrationTest {

    @Autowired
    protected WebTestClient webTestClient;

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected StoreRepository storeRepository;

    @Autowired
    protected TokenService tokenService;

    static String USER_TOKEN;

    static String STORE_ADMIN_TOKEN;

    static String ADMIN_TOKEN;

    static StoreEntity STORE_ENTITY_TEST;

    static UserEntity USER_ADMIN;

    static UserEntity USER_NORMAL;

    static UserEntity USER_STORE_ADMIN;

    static ProductEntity PRODUCT_1;
    static ProductEntity PRODUCT_2;
    static ProductEntity PRODUCT_3;




    @BeforeEach
    void setupProducts(@Autowired ProductRepository productRepository) {

        userRepository.deleteAll();
        storeRepository.deleteAll();
        productRepository.deleteAll();

        UserEntity normalUser = UserEntity.builder()
                .login( "NORMAL USER" )
                .password( "12345" )
                .storeId( null )
                .role( UserRoles.ROLE_USER )
                        .build();

        USER_NORMAL = userRepository.save( normalUser ).block();

        StoreEntity store = StoreEntity.builder()
                .name( "STORE TEST" )
                .description( "THIS IS A TEST STORE" )
                .city( "PORTO ALEGRE" )
                .address( "SANTA MARIA 490" )
                .state( "RIO GRANDE DO SUL" )
                .build();

        STORE_ENTITY_TEST = storeRepository.save(store).block();

        UserEntity adminStore = UserEntity.builder()
                .login( "STORE ADMIN USER" )
                .password( "12345" )
                .storeId( STORE_ENTITY_TEST.getId() )
                .role( UserRoles.ROLE_STORE_ADMIN )
                .build();

        USER_STORE_ADMIN = userRepository.save( adminStore ).block();

        UserEntity admin = UserEntity.builder()
                .login( "ADMIN USER" )
                .role( UserRoles.ROLE_ADMIN )
                .password( "12345" )
                .storeId( null )
                .build();
        USER_ADMIN = userRepository.save( admin ).block();

        ProductEntity product1 = ProductEntity.builder()
                .name( "PRODUCT 1" )
                .description( "THIS IS A PRODUCT 1 CREATED FOR TESTS" )
                .storeId( USER_STORE_ADMIN.getStoreId() )
                .price( new BigDecimal( 100.0 ) )
                .build();

        ProductEntity product2 = ProductEntity.builder()
                .name( "PRODUCT 2" )
                .description( "THIS IS A PRODUCT 2 CREATED FOR TESTS" )
                .storeId(USER_STORE_ADMIN.getStoreId())
                .price(new BigDecimal(200.0))
                .build();

        ProductEntity product3 = ProductEntity.builder()
                .name( "PRODUCT 3" )
                .description( "THIS IS A PRODUCT 3 CREATED FOR TESTS" )
                .storeId( USER_STORE_ADMIN.getStoreId() )
                .price( new BigDecimal( 300.0 ) )
                .build();

        PRODUCT_1 = productRepository.save( product1 ).block();
        PRODUCT_2 = productRepository.save( product2 ).block();
        PRODUCT_3 = productRepository.save( product3 ).block();

        USER_TOKEN = tokenService.generateToke(USER_NORMAL).block();
        STORE_ADMIN_TOKEN = tokenService.generateToke(USER_STORE_ADMIN).block();
        ADMIN_TOKEN = tokenService.generateToke(USER_ADMIN).block();



    }

    @Test
    void contextLoads() {
        assertThat(mongoDBContainer.isCreated()).isTrue();
        assertThat(mongoDBContainer.isRunning()).isTrue();
    }

}






