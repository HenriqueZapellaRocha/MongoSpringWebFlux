package com.example.demo.consumers;


import com.example.avro.CheckoutMessage;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static org.apache.kafka.common.requests.FetchMetadata.log;

@Slf4j
@Service
@Data
public class checkoutConsumer implements CommandLineRunner {

    private final ReactiveKafkaConsumerTemplate<String, CheckoutMessage> reactiveKafkaTemplate;


    @PostConstruct
    public Flux<CheckoutMessage> consume() {
        return reactiveKafkaTemplate
                .receiveAutoAck()
                .doOnNext(consumerRecord -> log.info("Received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset()))
                .map(ConsumerRecord::value)
                .doOnNext(message -> log.info("Successfully consumed {}={}", CheckoutMessage.class.getSimpleName(), message))
                .doOnError(throwable -> log.error("Error while consuming: {}", throwable.getMessage()));
    }

    @Override
    public void run(String... args) throws Exception {
        consume().subscribe();
    }
}
