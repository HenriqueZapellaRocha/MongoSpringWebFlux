package com.example.mongospringwebflux.v1.DTOS.responses.authDTOS;


public record AuthResponseDTO(
        String token,
        String username
) {
}
