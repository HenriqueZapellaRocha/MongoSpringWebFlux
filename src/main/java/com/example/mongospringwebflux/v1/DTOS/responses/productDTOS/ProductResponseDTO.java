package com.example.mongospringwebflux.v1.DTOS.responses.productDTOS;

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
        String store,
        String imageURL,
        Integer quantity
) {

    public static ProductResponseDTO entityToResponse( ProductEntity productEntity, String currency ) {
        return ProductResponseDTO.builder()
                .productID( productEntity.getProductID() )
                .name( productEntity.getName() )
                .price( new PriceResponse( currency, productEntity.getPrice() ) )
                .description( productEntity.getDescription() )
                .store( productEntity.getStoreId() )
                .imageURL( productEntity.getImageUrl() )
                .quantity( productEntity.getQuantity() )
                .build();
    }

    @Builder
    public record PriceResponse(
            String currency,
            BigDecimal value
    ) {
        public PriceResponse {
            if (value != null) {
                value = value.setScale( 2, RoundingMode.HALF_UP );
            }
        }
    }
}
