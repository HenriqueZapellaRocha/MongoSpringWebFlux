package com.example.mongospringwebflux.v1.DTOS.requests.checkoutDTOS;

public record CheckoutRequestDTO(

        String productId,
        Integer quantity
) {

}
