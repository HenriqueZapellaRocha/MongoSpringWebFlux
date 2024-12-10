package com.example.mongospringwebflux.v1.controller.DTOS.requests;

import jakarta.validation.constraints.NotBlank;

public record loginRequestDTO(
        @NotBlank(message = "login: blank login")
        String login,
        @NotBlank(message = "password: blank password")
        String password
) {
}
