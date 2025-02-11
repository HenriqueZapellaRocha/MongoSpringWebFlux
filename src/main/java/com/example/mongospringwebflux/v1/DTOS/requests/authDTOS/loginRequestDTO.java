package com.example.mongospringwebflux.v1.DTOS.requests.authDTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record loginRequestDTO(
        @NotBlank( message = "login: blank login" )
        String login,
        @NotBlank( message = "password: blank password" )
        String password
) {
}
