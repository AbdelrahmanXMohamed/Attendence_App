package com.example.demo.attendence.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {
    private Long id;


    private String name;

    private String username;

    private String password;

    private Integer type ;

    private String email ;

    private String phone ;

    private Long team_id ;

}
