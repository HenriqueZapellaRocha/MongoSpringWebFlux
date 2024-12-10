package com.example.mongospringwebflux.v1.controller.DTOS.requests;

public record StoreCreationRequestDTO (
        String name,
        String description,
        String address,
        String city,
        String state
) {

}
