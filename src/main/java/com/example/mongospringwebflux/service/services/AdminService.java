package com.example.mongospringwebflux.service.services;


import com.example.mongospringwebflux.exception.GlobalException;
import com.example.mongospringwebflux.exception.NotFoundException;
import com.example.mongospringwebflux.integration.exchange.ExchangeIntegration;
import com.example.mongospringwebflux.repository.ProductRepository;
import com.example.mongospringwebflux.repository.StoreRepository;
import com.example.mongospringwebflux.repository.UserRepository;
import com.example.mongospringwebflux.repository.entity.ProductEntity;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.ProductRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.ProductResponseDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ExchangeIntegration exchangeIntegration;

    public Mono<ProductResponseDTO> addProductToRelatedStore( ProductRequestDTO product, String from,
                                                             String to, String storeId ) {
        ProductEntity productEntity = product.toEntity();

        return storeRepository.findById( storeId )
                .switchIfEmpty( Mono.error( new NotFoundException( "The store don't exist" ) ) )
                .flatMap( storeEntity -> {
                    return exchangeIntegration.makeExchange( from,to )
                            .flatMap( exchangeRate -> {
                                productEntity.setPrice(product.price()
                                        .multiply( new BigDecimal(String.valueOf( exchangeRate ) ) ));
                                productEntity.setStoreId( storeEntity.getId() );
                                return productRepository.save( productEntity );
                            })
                            .map( savedProductEntity -> ProductResponseDTO.entityToResponse( savedProductEntity, to ) );
                });
    }

    public Mono<Void> deleteUserAndAllInformationRelated( String userId ) {
        return userRepository.findById( userId )
                .switchIfEmpty( Mono.error(new NotFoundException( "User not found" ) ))
                .flatMap( userEntity -> {
                    String storeId = userEntity.getStoreId();
                    return Mono.when(
                            userRepository.delete( userEntity ),
                            storeRepository.deleteById( userEntity.getStoreId() ),
                            productRepository.deleteAllByStoreId( storeId )

                    ).onErrorResume( e -> Mono.error( new GlobalException( "Error deleting user" )) );
                });
    }

    public Mono<Void> deleteManyProducts( List<String> productIds ) {
        return productRepository.deleteAllById( productIds );
    }

    public Flux<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .map( userEntity -> userEntity.entityToResponseDTO() );
    }
}
