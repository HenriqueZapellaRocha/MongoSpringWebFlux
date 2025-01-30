package com.example.mongospringwebflux.service.facades;


import com.example.mongospringwebflux.repository.entity.UserEntity;
import com.example.mongospringwebflux.repository.entity.enums.UserRoles;
import com.example.mongospringwebflux.service.services.KafkaService;
import lombok.Data;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Data
@Service
public class CheckoutFacade {

    private final KafkaService kafkaService;

    public Mono<Void> checkout( UserEntity user, String productId, Integer quantity ) {

        return Mono.just( user )
                .filter( userEntity -> ( userEntity.getRole().equals( UserRoles.ROLE_USER ) ) )
                .switchIfEmpty( Mono.defer( () -> Mono.error( new BadCredentialsException( "Unathorized" ) ) ))
                .then( kafkaService.publishCheckoutMessage( user, productId, quantity ) );
    }

}
