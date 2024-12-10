package com.example.mongospringwebflux.repository.entity.enums;


public enum UserRoles {

    ROLE_ADMIN( "ROLE_ADMIN" ),
    ROLE_STORE_ADMIN( "storeAdmin" ),
    ROLE_USER( "user" );

    private String role;

    UserRoles( String role ) { this.role = role; }

    public String getRole() { return role; }
}
