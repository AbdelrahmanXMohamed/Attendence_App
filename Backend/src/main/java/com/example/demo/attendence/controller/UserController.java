package com.example.demo.attendence.controller;

import com.example.demo.attendence.model.UserModel;
import com.example.demo.attendence.service.impl.UserSeviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private UserSeviceImpl userSevice ;

    @Autowired
    public UserController (UserSeviceImpl userSevice){
        this.userSevice = userSevice ;
    }

    @PostMapping
    public void createUser(@RequestBody UserModel userModel){

    }

    @PutMapping
    public void updateUser(@RequestBody UserModel userModel) {

    }


}
