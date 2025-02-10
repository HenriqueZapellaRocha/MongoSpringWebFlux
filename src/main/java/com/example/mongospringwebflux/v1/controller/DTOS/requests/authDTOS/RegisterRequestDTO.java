package com.example.mongospringwebflux.v1.controller.DTOS.requests.authDTOS;

import com.example.mongospringwebflux.repository.entity.enums.UserRoles;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RegisterRequestDTO(
        @NotBlank( message = "login: blank login" )
        String login,
        @NotBlank( message = "password: blank pasword" )
        String password,
        @NotNull( message = "role: role is blank" )
        UserRoles role,
        @NotNull( message = "email: You must inform a email" )
        String email,

        @Valid
        StoreCreationRequestDTO storeRelated

) {
}
