package com.example.mongospringwebflux.service.services;


import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
@Component
public class CookieService {

    public static void setCookie(ServerHttpResponse response, String value) {
        ResponseCookie cookie = ResponseCookie.from("last", value)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofDays(1))
                .build();

        response.addCookie(cookie);
    }
}



//package com.example.demo.service.services;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//@Component
//@RequiredArgsConstructor
//public class CookieService implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//
//        String requestURI = request.getRequestURI();
//        String productId = requestURI.substring(requestURI.lastIndexOf("/") + 1);
//
//
//        Cookie lastCookie = findCookie(request, "last");
//
//        if (lastCookie == null) {
//            lastCookie = new Cookie("last", productId);
//            lastCookie.setPath("/product/last");
//            lastCookie.setHttpOnly(true);
//            response.addCookie(lastCookie);
//        }
//
//        // Store cookie value in the request for controller access
//        request.setAttribute("lastCookieValue", lastCookie.getValue());
//        return true; // Allow the request to proceed
//    }
//
//    private Cookie findCookie(HttpServletRequest request, String name) {
//        if (request.getCookies() == null) {
//            return null;
//        }
//        for (Cookie cookie : request.getCookies()) {
//            if (name.equals(cookie.getName())) {
//                return cookie;
//            }
//        }
//        return null;
//    }
//}

