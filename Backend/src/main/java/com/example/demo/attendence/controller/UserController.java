package com.example.demo.attendence.controller;

import com.example.demo.attendence.model.UserRequestModel;
import com.example.demo.attendence.model.UserResponseModel;
import com.example.demo.attendence.services.impl.UserSeviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("users")
public class UserController {

    private UserSeviceImpl userSevice ;

    @Autowired
    public UserController (UserSeviceImpl userSevice){
        this.userSevice = userSevice ;
    }

    @PostMapping
    public UserResponseModel createUser(@RequestBody UserRequestModel userRequestModel){
        return userSevice.createUser(userRequestModel) ;
    }

    @PutMapping
    public UserResponseModel updateUser(@RequestBody UserRequestModel userRequestModel) {
        return null ;
    }

    @GetMapping("{id}")
    public UserResponseModel getUserById(@PathVariable Long id){
        return userSevice.getUser(id);
    }

    @GetMapping
    public List<UserResponseModel> getAllUsers(){
        return userSevice.getAllUsers();
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userSevice.deleteUser(id);
    }


}
