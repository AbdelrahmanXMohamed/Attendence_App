package com.example.demo.attendence.service;

import com.example.demo.attendence.model.UserModel;

import java.util.*;


public interface UserSevice {

    void createUser(UserModel userModel);

    void updateUser(UserModel userModel);

    UserModel getUser(Long id);

    List<UserModel> getAllUsers();

    void deleteUser (Long id);


}
