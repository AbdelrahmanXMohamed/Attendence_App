package com.example.demo.attendence.controller;

import com.example.demo.attendence.model.UserRequestModel;
import com.example.demo.attendence.model.UserResponseModel;
import com.example.demo.attendence.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:4200")
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

    @GetMapping
    public UserResponseModel getUser(){
        return userService.getUser();
    }
    @GetMapping("{id}")
    public UserResponseModel getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping
    public void updateUser(@RequestBody UserRequestModel userModel) {

    }

//    @GetMapping("{userId}")
//    public UserResponseModel getUser(@PathVariable Long userId){
//        return this.userService.getUser(userId);
//    }
    @GetMapping("name/{username}")
    public UserResponseModel getUserByUserName(@PathVariable String username){
       return this.userService.getUserByUserName(username);
    }


}
