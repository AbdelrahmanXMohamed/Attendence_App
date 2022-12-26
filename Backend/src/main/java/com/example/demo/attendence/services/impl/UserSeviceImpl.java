package com.example.demo.attendence.services.impl;

import com.example.demo.attendence.model.UserModel;
import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.mapper.UserMapper;
import com.example.demo.attendence.repository.UserRepository;
import com.example.demo.attendence.services.UserSevice;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserSeviceImpl implements UserSevice {

    private UserRepository userRepo ;
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    public UserSeviceImpl(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    public void createUser(UserModel userModel) {
        System.out.println(userModel);
        User user =userMapper.userToEntity(userModel);
        System.out.println(user);
        userRepo.save(user);

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
