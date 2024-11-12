package com.example.mongospringwebflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.scheduler.ReactorBlockHoundIntegration;

@Slf4j
@SpringBootApplication
public class MongoSpringWebFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoSpringWebFluxApplication.class, args);
    }

}
