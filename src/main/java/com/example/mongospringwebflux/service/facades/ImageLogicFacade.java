package com.example.mongospringwebflux.service.facades;


import com.example.mongospringwebflux.adapters.MinioAdapter;
import com.example.mongospringwebflux.repository.ProductRepository;
import com.example.mongospringwebflux.repository.UserRepository;
import com.example.mongospringwebflux.repository.entity.ProductEntity;
import com.example.mongospringwebflux.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@Service
public class ImageLogicFacade {

    private final ProductRepository productRepository;
    private final MinioAdapter minioAdapter;
    private final UserRepository userRepository;

    public Mono<String> validateAndPersistsImage( FilePart image, String productId, UserEntity currentUser ) {

        String newFileName = productId + '.' + FilenameUtils.getExtension( image.filename() );

        return productRepository.getByStoreId( currentUser.getStoreId() )
                .filter( product -> product.getProductID().equals( productId ) )
                .switchIfEmpty( Mono.defer( () -> Mono.error(
                        new BadCredentialsException( "This product don't exist or not related with this store" ) ) ))
                .flatMap( product -> {
                    product.setImageUrl( "http://localhost:9000/product-images/"+ newFileName );
                    return productRepository.save( product );
                } )
                .flatMap( product -> minioAdapter.uploadFile( Mono.just( image ), newFileName) )
                .then( Mono.just( "http://localhost:9000/product-images/"+ newFileName ) );
    }

    public Mono<Void> deleteImage( ProductEntity product ) {

        Pattern pattern = Pattern.compile( "[^/]*$" );
        Matcher matcher = pattern.matcher( product.getImageUrl() );



        if( !product.getImageUrl().equals( "has no image" ) && matcher.find() )
            return minioAdapter.deleteFile( matcher.group() );

        return Mono.empty();
    }
}
