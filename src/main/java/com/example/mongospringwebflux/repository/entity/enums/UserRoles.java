package com.example.mongospringwebflux.repository.entity.enums;

import lombok.Getter;

@Getter
public enum UserRoles {

    ROLE_ADMIN( "ROLE_ADMIN" ),
    ROLE_STORE_ADMIN( "ROLE_STORE_ADMIN" ),
    ROLE_USER( "ROLE_USER" );

    private final String role;

    UserRoles( String role ) { this.role = role; }

}
