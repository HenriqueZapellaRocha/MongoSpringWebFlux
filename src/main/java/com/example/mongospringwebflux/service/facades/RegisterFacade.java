package com.example.mongospringwebflux.service.facades;

import com.example.mongospringwebflux.repository.entity.enums.UserRoles;
import com.example.mongospringwebflux.service.services.StoreService;
import com.example.mongospringwebflux.service.services.securityServices.UserService;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.RegisterRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Data
@AllArgsConstructor
public class RegisterFacade {

    private final StoreService storeService;
    private final UserService userService;

    public Mono<Object> registerUser( RegisterRequestDTO registerRequest ) {

        if( registerRequest.role() == UserRoles.ROLE_STORE_ADMIN && registerRequest.storeRelated() == null )
            return Mono.error( new BadCredentialsException( "store in null" ));

        if ( registerRequest.role() == UserRoles.ROLE_STORE_ADMIN ) {
            return storeService.createStore( registerRequest.storeRelated() )
                    .flatMap(store -> userService.createUser( registerRequest, store.getId() ));
        } else {
            return userService.createUser( registerRequest, null );
        }
    }
}
