package com.example.demo.attendence.controller;

import com.example.demo.attendence.model.UserModel;
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
    public UserModel createUser(@RequestBody UserModel userModel){
        return userSevice.createUser(userModel) ;
    }

    @PutMapping
    public UserModel updateUser(@RequestBody UserModel userModel) {
        return null ;
    }

    @GetMapping("{id}")
    public UserModel getUserById(@PathVariable Long id){
        return userSevice.getUser(id);
    }

    @GetMapping
    public List<UserModel> getAllUsers(){
        return userSevice.getAllUsers();
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userSevice.deleteUser(id);
    }


}
