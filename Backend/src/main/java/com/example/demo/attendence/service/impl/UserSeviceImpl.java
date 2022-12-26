package com.example.demo.attendence.service.impl;

import com.example.demo.attendence.model.UserModel;
import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.mapper.UserMapper;
import com.example.demo.attendence.repository.UserRepo;
import com.example.demo.attendence.service.UserSevice;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserSeviceImpl implements UserSevice {

    private UserRepo userRepo ;
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    public UserSeviceImpl(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public void createUser(UserModel userModel) {

    }

    @Override
    public void updateUser(UserModel userModel) {

    }

    @Override
    public UserModel getUser(Long id) {
        User userGetted = userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Not fond this product"));
        return userMapper.userToModel(userGetted);
    }

    @Override
    public List<UserModel> getAllUsers() {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
