package com.example.mongospringwebflux.v1.controller.DTOS.requests;

import com.example.mongospringwebflux.repository.entity.enums.UserRoles;

public record RegisterRequestDTO (
        String login,
        String password,
        UserRoles role,
        String storeRelated

) { }
