package com.example.demo.attendence.mapper;

import com.example.demo.attendence.model.UserRequestModel;
import com.example.demo.attendence.model.UserResponseModel;
import com.example.demo.attendence.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" , uses = {TeamMapper.class})
public interface UserMapper {

    User userToEntity (UserRequestModel userRequestModel);

    UserResponseModel userToModel (User User);
}
