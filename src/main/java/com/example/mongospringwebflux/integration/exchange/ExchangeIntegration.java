package com.example.mongospringwebflux.integration.exchange;



import com.example.mongospringwebflux.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;



@Service
@RequiredArgsConstructor
public class ExchangeIntegration {

    private final WebClient webClient;

    public Mono<Double> makeExchange(String from, String to ) {

        return webClient
                .get()
                .uri("/pair/{from}/{to}", from, to)
                .retrieve()
                .bodyToMono(ExchangeResponse.class)
                .map(ExchangeResponse::conversion_rate)
                    .onErrorResume(throwable -> Mono.error( new NotFoundException( "Currency not found" ) ) );
    }
}
