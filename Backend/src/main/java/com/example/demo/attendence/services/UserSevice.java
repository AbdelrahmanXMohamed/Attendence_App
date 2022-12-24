package com.example.demo.attendence.services;

import com.example.demo.attendence.model.UserModel;

import java.util.List;


public interface UserSevice {

     UserModel createUser(UserModel userModel);

    UserModel updateUser(UserModel userModel);

    UserModel getUser(Long id);

    List<UserModel> getAllUsers();

    void deleteUser (Long id);


}
