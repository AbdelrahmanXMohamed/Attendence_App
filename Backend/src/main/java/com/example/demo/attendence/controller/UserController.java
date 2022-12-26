package com.example.demo.attendence.controller;

import com.example.demo.attendence.model.UserRequestModel;
import com.example.demo.attendence.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public void createUser(@RequestBody UserRequestModel userModel) {
        userService.createUser(userModel);

    }

    @PutMapping
    public void updateUser(@RequestBody UserRequestModel userModel) {

    }


}
