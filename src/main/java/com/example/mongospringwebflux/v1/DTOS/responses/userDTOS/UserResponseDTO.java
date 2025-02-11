package com.example.mongospringwebflux.v1.DTOS.responses.userDTOS;


import com.example.mongospringwebflux.repository.entity.enums.UserRoles;
import lombok.Builder;



@Builder
public record UserResponseDTO(

        String id,
        String login,
        UserRoles role,
        String storeRelated
) {
}
