package com.example.mongospringwebflux.service.services;

import com.example.avro.CheckoutMessage;
import com.example.mongospringwebflux.repository.entity.UserEntity;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.checkoutDTOS.CheckoutRequestDTO;
import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Data
@Service
public class KafkaService {

    private final ReactiveKafkaProducerTemplate<String, CheckoutMessage> producerTemplate;

    public Mono<Void> publishCheckoutMessage( UserEntity userEntity, CheckoutRequestDTO checkoutRequest,
                                                                                            String currency )  {
        return producerTemplate.send(

                "checkout-processing", UUID.randomUUID().toString(), CheckoutMessage.newBuilder()
                                .setUserId( userEntity.getId() )
                                .setLogin( userEntity.getLogin() )
                                .setRole( userEntity.getRole().getRole() )
                                .setUserEmail( userEntity.getUserEmail() )
                                .setProductId( checkoutRequest.productId() )
                                .setQuantity( checkoutRequest.quantity() )
                                .setCurrency( currency )
                        .build()
        ).then();
    }
}
