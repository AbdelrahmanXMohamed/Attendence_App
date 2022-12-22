package com.example.demo.attendence.mapper;

import com.example.demo.attendence.dto.UserModel;
import com.example.demo.attendence.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" )
public interface UserMapper {

    User userToEntity (UserModel userModel);

    UserModel userToModel (User User);
}
