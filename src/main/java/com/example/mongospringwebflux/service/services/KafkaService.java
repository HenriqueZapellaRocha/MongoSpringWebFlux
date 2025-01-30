package com.example.mongospringwebflux.service.services;

import com.example.avro.CheckoutMessage;
import com.example.mongospringwebflux.repository.entity.UserEntity;
import lombok.Data;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Data
@Service
public class KafkaService {

    private final ReactiveKafkaProducerTemplate<String, CheckoutMessage> producerTemplate;

    public Mono<Void> publishCheckoutMessage( UserEntity userEntity, String productId, Integer quantity ) {
        return producerTemplate.send(

                "checkout-processing", UUID.randomUUID().toString(), CheckoutMessage.newBuilder()
                                .setUserId( userEntity.getId() )
                                .setLogin( userEntity.getLogin() )
                                .setRole( userEntity.getRole().getRole() )
                                .setUserEmail( userEntity.getUserEmail() )
                                .setProductId( productId )
                                .setQuantity( quantity )
                        .build()
        ).then();
    }
}
