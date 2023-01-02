package com.example.demo.attendence.service;

import com.example.demo.attendence.model.RegistrationRequestModel;
import com.example.demo.attendence.model.UserRequestModel;
import com.example.demo.attendence.model.UserResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

    UserResponseModel createUser(UserRequestModel userRequestModel);

    UserResponseModel updateUser(UserRequestModel userRequestModel);

    UserResponseModel getUser(Long id);

    List<UserResponseModel> getAllUsers();

    void deleteUser (Long id);

<<<<<<<<< Temporary merge branch 1:Backend/src/main/java/com/example/demo/attendence/service/UserSevice.java

    UserResponseModel getUserByUserName(String username);
=========
    String register(RegistrationRequestModel registrationRequestModel);
    String confirmToken(String token);
>>>>>>>>> Temporary merge branch 2:Backend/src/main/java/com/example/demo/attendence/service/UserService.java
}
