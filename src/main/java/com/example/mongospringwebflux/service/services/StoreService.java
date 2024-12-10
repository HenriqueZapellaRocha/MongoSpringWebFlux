package com.example.mongospringwebflux.service.services;

import com.example.mongospringwebflux.repository.StoreRepository;
import com.example.mongospringwebflux.repository.entity.StoreEntity;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.StoreCreationRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.StoreResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
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

    public Flux<StoreResponseDTO> getAllStores() {
        return storeRepository.findAll().map( storeEntity ->
                StoreResponseDTO.entityToResponse( storeEntity ) );
    }

    public Mono<StoreResponseDTO> getStoreById( String id ) {
        return storeRepository.findById( id )
                .map(StoreResponseDTO::entityToResponse );
    }
}