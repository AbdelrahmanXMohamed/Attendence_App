package com.example.demo.attendence.model;

import com.example.demo.attendence.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class TeamResponseModel {

    private Long id;

    private String name;

    private String description;

    private User manager;

    private List<User> users;
}
