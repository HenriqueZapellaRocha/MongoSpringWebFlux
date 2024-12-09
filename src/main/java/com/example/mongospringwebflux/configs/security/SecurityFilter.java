package com.example.mongospringwebflux.configs.security;


import com.example.mongospringwebflux.repository.UserRepository;
import com.example.mongospringwebflux.service.services.securityServices.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
public class SecurityFilter implements WebFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = this.recoverToken(exchange);

        if (token != null) {
            return tokenService.validateToke(token)
                    .flatMap(login -> userRepository.findByLogin(login)
                            .flatMap(user -> {
                                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                                return chain.filter(exchange)
                                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                            })
                    )
                    .onErrorResume(e -> chain.filter(exchange));
        }

        return chain.filter(exchange);
    }



    private String recoverToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}


