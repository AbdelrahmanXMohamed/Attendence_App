package com.example.demo.attendence.model;

import com.example.demo.attendence.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRequestModel {

    private String name;

    private String description;

    private User manager;
}
