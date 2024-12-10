package com.example.mongospringwebflux.repository;

import com.example.mongospringwebflux.repository.entity.StoreEntity;
import com.example.mongospringwebflux.repository.entity.UserEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository< UserEntity, String>  {

    Mono<UserDetails> findByLogin( String login );

    @Query(value = "{ 'storeRelated': { $exists: true } }", fields = "{ 'storeRelated': 1, '_id': 0 }")
    Flux<StoreEntity> findAllStores();

}
