package com.example.mongospringwebflux.controllers;

import com.example.mongospringwebflux.service.services.ProductService;
import com.example.mongospringwebflux.v1.controller.ProductController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@WebFluxTest(controllers = ProductController.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class ProductControllerTest {

    @Mock
    private ProductService productService;

}
