package com.example.demo.consumers;


import com.example.avro.CheckoutMessage;
import com.example.demo.services.checkoutServices.facedes.CheckoutFacade;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@Data
public class checkoutConsumer /*implements CommandLineRunner*/ {

    private final ReactiveKafkaConsumerTemplate<String, CheckoutMessage> reactiveKafkaTemplate;
    private final CheckoutFacade checkoutFacade;


//    @PostConstruct
//    public Mono<Void> consume() {
//        return reactiveKafkaTemplate
//                .receiveAutoAck()
//                .flatMap(consumerRecord ->  {
//                    System.out.println(consumerRecord.value().toString());
//
//                    return checkoutFacade.makeCheckout(consumerRecord.value(), consumerRecord.key());
//                })
//                .doOnError(throwable -> log.error("Error while consuming: {}", throwable.getMessage()))
//                .then();
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        consume().subscribe();
//    }

    @KafkaListener( topics = "checkout-processing", groupId = "group" )
    public Mono<Void> consume( @Payload ConsumerRecord<String, CheckoutMessage> record ) {

        return checkoutFacade.makeCheckout( record.value(), record.key() );
    }
}
