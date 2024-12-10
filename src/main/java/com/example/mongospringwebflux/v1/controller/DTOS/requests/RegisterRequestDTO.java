package com.example.mongospringwebflux.v1.controller.DTOS.requests;

import com.example.mongospringwebflux.repository.entity.enums.UserRoles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(
        @NotBlank(message = "login: blank login")
        String login,
        @NotBlank(message = "password: blank pasword")
        String password,
        @NotNull(message = "role: role is blank")
        UserRoles role,
        StoreCreationRequestDTO storeRelated

) {
}
