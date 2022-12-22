package com.example.demo.attendence.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRequestModel {


    private String name;
    private String description;

    private User manager;
}
