package com.example.mongospringwebflux.v1.controller;


import com.example.mongospringwebflux.repository.entity.UserEntity;
import com.example.mongospringwebflux.service.facades.CheckoutFacade;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.checkoutDTOS.CheckoutRequestDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
@Data
public class CheckoutController {

    private final CheckoutFacade checkoutFacade;

    @PostMapping
    public Mono<Void> checkout( @RequestBody CheckoutRequestDTO checkoutRequestDTO,
                                @RequestParam( name = "currency" ) String currency,
                                @AuthenticationPrincipal UserEntity currentUser ) {

        return checkoutFacade.checkout( currentUser, checkoutRequestDTO, currency );
    }

}
