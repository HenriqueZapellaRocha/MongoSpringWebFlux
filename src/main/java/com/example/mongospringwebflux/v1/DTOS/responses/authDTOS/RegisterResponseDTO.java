package com.example.mongospringwebflux.v1.DTOS.responses.authDTOS;

import lombok.Builder;

@Builder
public record RegisterResponseDTO(

        String login
) {

}
