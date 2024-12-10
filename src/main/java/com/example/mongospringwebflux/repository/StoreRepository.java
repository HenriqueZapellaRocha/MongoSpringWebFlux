package com.example.mongospringwebflux.repository;

import com.example.mongospringwebflux.repository.entity.StoreEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface StoreRepository extends ReactiveMongoRepository<StoreEntity, String> {

    Mono<StoreEntity> findByName(String name);
}
