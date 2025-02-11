package com.example.mongospringwebflux.exceptions.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotFoundExceptionDTO {


    private String error;
}
