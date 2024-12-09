package com.example.mongospringwebflux.v1.controller.DTOS.requests;

public record loginRequestDTO(
        String login,
        String password
) {
}
