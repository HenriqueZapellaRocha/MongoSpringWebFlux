package com.example.mongospringwebflux.v1.controller.DTOS.requests;

public record CheckoutRequestDTO(

        String productId,
        Integer quantity
) {

}
