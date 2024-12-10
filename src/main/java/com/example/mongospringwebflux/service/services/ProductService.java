package com.example.mongospringwebflux.service.services;



import java.math.BigDecimal;
import java.util.List;

import com.example.mongospringwebflux.exception.NotFoundException;
import com.example.mongospringwebflux.integration.exchange.ExchangeIntegration;
import com.example.mongospringwebflux.repository.ProductRepository;
import com.example.mongospringwebflux.repository.StoreRepository;
import com.example.mongospringwebflux.repository.entity.ProductEntity;
import com.example.mongospringwebflux.repository.entity.UserEntity;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.ProductRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ExchangeIntegration exchangeIntegration;
    private final StoreRepository storeRepository;

    public Mono<ProductResponseDTO> add( ProductRequestDTO product, String from, String to, UserEntity currentUser ) {
        ProductEntity productEntity = product.toEntity();

        return storeRepository.findById(currentUser.getStoreId())
                .flatMap(storeEntity -> {
                    return exchangeIntegration.makeExchange( from,to )
                            .flatMap( exchangeRate -> {
                                productEntity.setPrice(product.price().multiply(new BigDecimal(String.valueOf(exchangeRate))));
                                productEntity.setStoreId( storeEntity.getId() );
                                return productRepository.save( productEntity );
                            }).map( savedProductEntity -> ProductResponseDTO.entityToResponse( savedProductEntity, to ) );
                });
    }


    public Mono<ProductResponseDTO> getById( String id, String from, String to ) {
        return productRepository.findById( id )
                        .switchIfEmpty( Mono.error( new NotFoundException( "No product found" ) ) )
                .zipWith( exchangeIntegration.makeExchange( from,to ), ( product,exchangeRate ) -> {
                    product.setPrice( product.getPrice()
                            .multiply( new BigDecimal( String.valueOf( exchangeRate ) ) ) );
                    return product;
                }).map(savedProductEntity -> ProductResponseDTO.entityToResponse( savedProductEntity, to ) );
    }

    public Mono<ProductResponseDTO> update( ProductRequestDTO product, String id ) {
        ProductEntity productEntity = product.toEntity( id );
        return productRepository.findById( id )
                .switchIfEmpty( Mono.error ( new NotFoundException( "No product found" ) ) )
                .flatMap( existingProduct -> {
                    existingProduct = productEntity;
                    return productRepository.save( existingProduct );
                }).map( productSaved -> ProductResponseDTO.entityToResponse( productSaved, "USD" ) );

    }

    public Flux<ProductResponseDTO> getAll( String from, String to ) {
        return exchangeIntegration.makeExchange(from,to)
                .flatMapMany( exchangeRate -> productRepository.findAll()
                        .map( productEntity -> {
                            productEntity.setPrice(
                                    productEntity.getPrice()
                                            .multiply( new BigDecimal( String.valueOf( exchangeRate ) ) ) );
                            return ProductResponseDTO.entityToResponse( productEntity, to );
                        }));
    }

    public Mono<Void> deleteMany( List<String> ids ) {
        return productRepository.deleteAllById( ids );
    }
}

