package com.example.demo.services.checkoutServices;

import com.example.avro.CheckoutMessage;
import com.example.avro.ConclusionMessage;
import lombok.Data;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Data
public class CheckoutTopicProducers {

    private final ReactiveKafkaProducerTemplate<String, ConclusionMessage> producerTemplate;

    public Mono<Void> publishCheckoutMessage( CheckoutMessage checkoutMessage ) {
        return producerTemplate.send(

                "checkout-completed", UUID.randomUUID().toString(), ConclusionMessage.newBuilder()
                                .setError( null )
                                .setProductId( checkoutMessage.getProductId() )
                                .setUserEmail(checkoutMessage.getUserEmail())
                                .setQuantity( checkoutMessage.getQuantity() )
                                .setUserId( checkoutMessage.getUserId() )
                                .setCurrency( checkoutMessage.getCurrency() )
                        .build()
        ).then();
    }

    public Mono<Void> invalidCheckout( CheckoutMessage checkoutMessage, String error ) {

        return producerTemplate.send(

                "checkout-invalid", UUID.randomUUID().toString(), ConclusionMessage.newBuilder()
                        .setError( error )
                        .setProductId( checkoutMessage.getProductId() )
                        .setUserEmail(checkoutMessage.getUserEmail())
                        .setQuantity( checkoutMessage.getQuantity() )
                        .setUserId(checkoutMessage.getUserId() )
                        .build()
        ).then();
    }

}
