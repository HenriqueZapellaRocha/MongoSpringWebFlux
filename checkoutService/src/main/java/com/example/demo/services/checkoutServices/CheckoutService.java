package com.example.demo.services.checkoutServices;

import com.example.demo.domain.models.ProductEntity;
import com.example.demo.repository.ProductRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Data
public class CheckoutService {

    private final ProductRepository productRepository;

    public Mono<Void> checkout( ProductEntity productEntity, Integer quantity ) {

        productEntity.setQuantity( productEntity.getQuantity() - quantity );

        if( productEntity.getQuantity() > 0 )
            return productRepository.save( productEntity ).then();
        else
            return productRepository.delete( productEntity ).then();
    }
}
