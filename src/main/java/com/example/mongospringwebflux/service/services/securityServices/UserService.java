package com.example.mongospringwebflux.service.services.securityServices;

import com.example.mongospringwebflux.repository.UserRepository;
import com.example.mongospringwebflux.repository.entity.UserEntity;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.AuthResponseDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.RegisterRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.RegisterResponseDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.loginRequestDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private ReactiveAuthenticationManager authenticationManager;
    private TokenService tokenService;

    public Mono<Object> register(RegisterRequestDTO registerRequest ) {
        return userRepository.findByLogin(registerRequest.login())
                .flatMap(existingUser -> Mono.error(new RuntimeException("User already exists")))
                .switchIfEmpty(
                        Mono.just(registerRequest.password())
                                .map(password -> new BCryptPasswordEncoder().encode(password))
                                .flatMap(encryptedPassword -> {
                                    // Criação de um novo usuário
                                    UserEntity newUser = UserEntity.builder()
                                            .login(registerRequest.login())
                                            .password(encryptedPassword)
                                            .role(registerRequest.role())
                                            .storeRelated(registerRequest.storeRelated())
                                            .build();

                                    return userRepository.save(newUser)
                                            .map(savedUser -> new RegisterResponseDTO(
                                                    savedUser.getLogin(),
                                                    savedUser.getLogin()
                                            ));
                                })
                );
    }

    public Mono<AuthResponseDTO> login(@RequestBody @Valid loginRequestDTO login) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(login.login(), login.password());

        return authenticationManager.authenticate(usernamePassword)
                .flatMap(authentication -> {
                    UserEntity user = (UserEntity) authentication.getPrincipal();

                    return tokenService.generateToke(user)
                            .map(token -> new AuthResponseDTO(token, user.getLogin()));
                });
    }

}
