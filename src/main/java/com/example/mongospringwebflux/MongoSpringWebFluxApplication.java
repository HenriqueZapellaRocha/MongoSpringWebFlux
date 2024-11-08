package com.example.mongospringwebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.scheduler.ReactorBlockHoundIntegration;

@SpringBootApplication
public class MongoSpringWebFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoSpringWebFluxApplication.class, args);
    }

}
