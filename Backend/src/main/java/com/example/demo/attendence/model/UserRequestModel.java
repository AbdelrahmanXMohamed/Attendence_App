package com.example.demo.attendence.model;

import lombok.Data;

@Data
public class UserRequestModel {
    private Long id;

    private String name;

    private String username;

    private String password;

//    private Integer type ;

    private String email ;

    private String phone ;

}
