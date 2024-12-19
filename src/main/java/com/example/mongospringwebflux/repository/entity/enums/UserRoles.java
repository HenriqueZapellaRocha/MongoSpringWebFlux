package com.example.mongospringwebflux.repository.entity.enums;

import lombok.Getter;

@Getter
public enum UserRoles {

    ROLE_ADMIN( "admin" ),
    ROLE_STORE_ADMIN( "storeAdmin" ),
    ROLE_USER( "user" );

    private final String role;

    UserRoles( String role ) { this.role = role; }

}
