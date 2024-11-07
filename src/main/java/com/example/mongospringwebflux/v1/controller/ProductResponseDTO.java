package com.example.mongospringwebflux.v1.controller;

import com.example.mongospringwebflux.repository.entity.ProductEntity;
import lombok.Builder;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Builder
public record ProductResponseDTO(
        String productID,
        String name,
        PriceResponse price
) {

    public static ProductResponseDTO entityToResponse(ProductEntity productEntity, String currency) {
        return ProductResponseDTO.builder()
                .productID(productEntity.getProductID())
                .name(productEntity.getName())
                .price(new PriceResponse(currency, productEntity.getPrice()))
                .build();
    }

    @Builder
    public record PriceResponse(
            String currency,
            BigDecimal value
    ) {
        public PriceResponse {
            if (value != null) {
                value = value.setScale(2, RoundingMode.HALF_UP);
            }
        }
    }
}
