package com.example.demo.attendence.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationRequestModel {

    private final String firstName ;
    private final String lastName ;
    private final String email ;
    private final String password ;

}
