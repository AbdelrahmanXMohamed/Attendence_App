package com.example.demo.attendence.services;

import com.example.demo.attendence.dto.UserModel;
import com.example.demo.attendence.entity.User;

import org.springframework.stereotype.Service;

import java.util.*;


public interface UserSevice {

    void createUser(UserModel userModel);

    void updateUser(UserModel userModel);

    UserModel getUser(Long id);

    List<UserModel> getAllUsers();

    void deleteUser (Long id);


}
