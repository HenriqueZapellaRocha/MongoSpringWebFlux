package com.example.mongospringwebflux.v1.controller.DTOS.requests.checkoutDTOS;

public record CheckoutRequestDTO(

        String productId,
        Integer quantity
) {

}
