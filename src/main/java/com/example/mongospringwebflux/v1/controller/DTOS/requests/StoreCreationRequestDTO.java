package com.example.mongospringwebflux.v1.controller.DTOS.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record StoreCreationRequestDTO (

        @NotBlank(message = "Store name: blank name")
        String name,
        @NotBlank(message = "Store description: blank description")
        String description,
        @NotBlank(message = "Store address: blank address")
        String address,
        @NotBlank(message = "Store city: blank city")
        String city,
        @NotBlank(message = "Store state: blank state")
        String state
) {

}
