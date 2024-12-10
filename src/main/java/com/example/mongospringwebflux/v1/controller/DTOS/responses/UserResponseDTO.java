package com.example.mongospringwebflux.v1.controller.DTOS.responses;


import com.example.mongospringwebflux.repository.entity.enums.UserRoles;
import lombok.Builder;
import lombok.Data;


@Builder
public record UserResponseDTO(

        String login,
        UserRoles role,
        String storeRelated
) {
}
