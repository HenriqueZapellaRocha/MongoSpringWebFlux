package com.example.demo.configs.kafka;

import com.example.avro.CheckoutMessage;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ReactiveKafkaConsumerTemplate<String, CheckoutMessage> consumerFactory() {

        Map<String, Object> props = new HashMap<>();
        props.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094" );
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "meu-grupo-consumidor");
        props.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
        props.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class );
        props.put( "schema.registry.url", "http://localhost:8081" );
        props.put( "specific.avro.reader", "true" );

        ReceiverOptions<String, CheckoutMessage> reciverOptions = ReceiverOptions.create(props);



        return new ReactiveKafkaConsumerTemplate<>(
                reciverOptions.subscription( Collections.singletonList("checkout-processing")) );
    }
    
}
