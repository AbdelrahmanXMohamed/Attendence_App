package com.example.demo.attendence.service;

import com.example.demo.attendence.model.RegistrationRequestModel;
import com.example.demo.attendence.model.UserRequestModel;
import com.example.demo.attendence.model.UserResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

    UserResponseModel createUser(UserRequestModel userRequestModel);

    UserResponseModel updateUser(UserRequestModel userRequestModel);

    UserResponseModel getUser();

    List<UserResponseModel> getAllUsers();

    void deleteUser (Long id);

    UserResponseModel getUserById(Long id);
    UserResponseModel getUserByUserName(String username);
    String register(UserRequestModel registrationRequestModel);
    String confirmToken(String token);
}
