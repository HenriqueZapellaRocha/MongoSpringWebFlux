package com.example.mongospringwebflux.exceptions.dtos;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class InvalidInputValuesExceptionDTO {

    private List<String> errors;


}
