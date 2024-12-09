package com.example.mongospringwebflux.v1.controller.DTOS.responses;

import com.example.mongospringwebflux.repository.entity.ProductEntity;
import lombok.Builder;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Builder
public record ProductResponseDTO(
        String productID,
        String name,
        PriceResponse price,
        String description,
        String store
) {

    public static ProductResponseDTO entityToResponse(ProductEntity productEntity, String currency) {
        return ProductResponseDTO.builder()
                .productID(productEntity.getProductID())
                .name(productEntity.getName())
                .price(new PriceResponse(currency, productEntity.getPrice()))
                .description(productEntity.getDescription())
                .store(productEntity.getStoreId())
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
