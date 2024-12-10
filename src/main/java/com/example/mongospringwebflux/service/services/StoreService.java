package com.example.mongospringwebflux.service.services;

import com.example.mongospringwebflux.repository.StoreRepository;
import com.example.mongospringwebflux.repository.entity.StoreEntity;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.StoreCreationRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Data
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public Mono<StoreEntity> createStore( StoreCreationRequestDTO storeRequest ) {
        return storeRepository.findByName(storeRequest.name())
                .switchIfEmpty(
                        Mono.just(StoreEntity.builder()
                                        .name(storeRequest.name())
                                        .description(storeRequest.description())
                                        .address(storeRequest.address())
                                        .city(storeRequest.city())
                                        .state(storeRequest.state())

                                        .build())
                                .flatMap(storeRepository::save)
                );
    }
}