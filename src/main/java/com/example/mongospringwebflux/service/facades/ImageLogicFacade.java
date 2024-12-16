package com.example.mongospringwebflux.service.facades;


import com.example.mongospringwebflux.exception.GlobalException;
import com.example.mongospringwebflux.repository.ProductRepository;
import com.example.mongospringwebflux.repository.UserRepository;
import com.example.mongospringwebflux.repository.entity.UserEntity;
import com.example.mongospringwebflux.service.services.MinioService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
@Service
public class ImageLogicFacade {

    private final ProductRepository productRepository;
    private final MinioService minioService;
    private final UserRepository userRepository;

    public Mono<Void> validateAndPersistsImage( FilePart image, String productId, UserEntity currentUser ) {

        return productRepository.getByStoreId( currentUser.getStoreId() )
                .filter( product -> product.getProductID().equals( productId ) )
                .switchIfEmpty( Mono.error(
                        new BadCredentialsException( "This product don't exist or not related with this store" ) ) )

                .flatMap( product -> minioService.uploadFile( Mono.just( image ), product.getProductID() ) )
                .then();
    }
}
