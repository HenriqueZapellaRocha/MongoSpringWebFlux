package com.example.mongospringwebflux.MockBuiilders;

import com.example.mongospringwebflux.v1.controller.ProductResponseDTO;

import java.math.BigDecimal;

public class MockBuilder {

    public static ProductResponseDTO productResponse() {

        return ProductResponseDTO.builder()
                .productID("DDDDDDD123123123")
                .name("JVM")
                .price( new ProductResponseDTO.PriceResponse("BRL", new BigDecimal(200)) )
                .build();

    }
}
