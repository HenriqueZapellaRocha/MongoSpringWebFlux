package com.example.mongospringwebflux.configs.kafka;

import com.example.Person;
import com.example.avro.CheckoutMessage;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
//@EnableKafkaStreams
public class KafkaConfig {

    @Bean
    public ReactiveKafkaProducerTemplate<String, CheckoutMessage> producerFactory() {

        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://localhost:8081");

        SenderOptions<String, CheckoutMessage> senderOptions = SenderOptions.create(props);

        return new ReactiveKafkaProducerTemplate<>(senderOptions);
    }

    @Bean
    public ConsumerFactory<String, Person> consumerFactory() {

        Map<String, Object> props = new HashMap<>();
        props.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094" );
        props.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
        props.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class );
        props.put( "schema.registry.url", "http://localhost:8081" );
        props.put( "specific.avro.reader", "true" );

        return new DefaultKafkaConsumerFactory<>( props );
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Person> kafkaListenerContainerFactory() {


        ConcurrentKafkaListenerContainerFactory<String, Person>
                factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory( consumerFactory() );

        return factory;
    }

//    @Bean( name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME )
//    public KafkaStreamsConfiguration kStreamsConfig() {
//
//        Map<String, Object> props = new HashMap<>();
//        props.put( APPLICATION_ID_CONFIG, "servicess" );
//        props.put( BOOTSTRAP_SERVERS_CONFIG, "localhost:9094" );
//        props.put( "schema.registry.url", "http://localhost:8081" );
//        props.put( DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass() ) ;
//        props.put( DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class );
//
//        props.put( "specific.avro.reader", "true" );
//
//        return new KafkaStreamsConfiguration( props );
//    }
}