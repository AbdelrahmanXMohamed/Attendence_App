package com.example.demo.attendence.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserResponseModel {
    private Long id;

    private String name;

    private String username;

    private String password;

    private String email;

    private String phone ;


}
