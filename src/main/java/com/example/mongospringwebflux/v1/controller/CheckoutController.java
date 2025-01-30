package com.example.mongospringwebflux.v1.controller;


import com.example.mongospringwebflux.repository.entity.UserEntity;
import com.example.mongospringwebflux.service.facades.CheckoutFacade;
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

    @GetMapping("/{productId}/{quantity}")
    public Mono<Void> checkout( @PathVariable String productId,
                                @PathVariable Integer quantity,
                                @AuthenticationPrincipal UserEntity currentUser ) {

        return checkoutFacade.checkout( currentUser, productId, quantity );
    }

}
