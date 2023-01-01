package com.example.demo.attendence.service;

import com.example.demo.attendence.model.UserRequestModel;
import com.example.demo.attendence.model.UserResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserSevice extends UserDetailsService {

    UserResponseModel createUser(UserRequestModel userRequestModel);

    UserResponseModel updateUser(UserRequestModel userRequestModel);

    UserResponseModel getUser(Long id);

    List<UserResponseModel> getAllUsers();

    void deleteUser (Long id);


}
