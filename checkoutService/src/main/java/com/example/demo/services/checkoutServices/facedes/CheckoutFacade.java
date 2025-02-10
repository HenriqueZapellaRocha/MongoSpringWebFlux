package com.example.demo.services.checkoutServices.facedes;

import com.example.avro.CheckoutMessage;
import com.example.demo.repository.ProductRepository;
import com.example.demo.services.checkoutServices.CheckoutService;
import com.example.demo.services.checkoutServices.CheckoutTopicProducers;
import com.example.demo.services.emailServices.EmailService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Data
@Slf4j
public class CheckoutFacade {

    private final ProductRepository productRepository;
    private final CheckoutService checkoutService;
    private final CheckoutTopicProducers checkoutTopicProducers;
    private final EmailService emailService;

    public Mono<Void> makeCheckout( CheckoutMessage checkoutMessage, String key ) {

        return productRepository.findById( checkoutMessage.getProductId() )
                .switchIfEmpty( Mono.defer( () ->
                        checkoutTopicProducers.invalidCheckout( checkoutMessage, "product not found" )
                        .then( Mono.error( new Exception( "Product not found" ) ))))

                .filter( productEntity -> checkoutMessage.getQuantity() >= 0 )
                .switchIfEmpty(Mono.defer(() ->
                        checkoutTopicProducers.invalidCheckout( checkoutMessage, "Negative quantity" )
                                .then( Mono.error( new Exception( "Negative quantity" ) ))))

                .filter( productEntity -> checkoutMessage.getQuantity() <= productEntity.getQuantity() )

                .switchIfEmpty( Mono.defer(() ->
                        checkoutTopicProducers.invalidCheckout( checkoutMessage, "Quantity bigger then exist" )
                        .then( Mono.error( new Exception( "Quantity bigger then exist" ) )))
                )

                .flatMap( productEntity ->
                                Mono.when( checkoutService.checkout( productEntity, checkoutMessage.getQuantity() ),
                                           checkoutTopicProducers.publishCheckoutMessage( checkoutMessage ),
                                           emailService.sendEmail( checkoutMessage, productEntity, key )
                                )
                )

                .onErrorResume( throwable -> {
                    log.error("Error in checkout processing: {}", throwable.getMessage());

                    return Mono.empty();
                } );
    }


}
