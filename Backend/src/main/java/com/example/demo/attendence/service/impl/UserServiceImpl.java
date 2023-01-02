package com.example.demo.attendence.service.impl;

import com.example.demo.attendence.model.RegistrationRequestModel;
import com.example.demo.attendence.model.UserRequestModel;
import com.example.demo.attendence.model.UserResponseModel;
import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.mapper.UserMapper;
import com.example.demo.attendence.repository.UserRepository;
import com.example.demo.attendence.service.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepository = userRepo;
    }

    @Override
    public UserResponseModel createUser(UserRequestModel userModel) {
        System.out.println(userModel);
        User user = userMapper.userToEntity(userModel);
        System.out.println(user);
        return userMapper.userToModel(userRepository.save(user));
    }
    @Override
    public UserResponseModel updateUser(UserRequestModel userModel) {
        return null;
    }

    @Override
    public UserResponseModel getUser(Long id) {
        User userGot = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Not fond this product"));
        return userMapper.userToModel(userGot);
    }
    @Override
    public List<UserResponseModel> getAllUsers() {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public UserResponseModel getUserByUserName(String username) {
        return userMapper.userToModel(this.userRepository.findByUsername(username).orElseThrow());
    }

    @Override
    public String register(RegistrationRequestModel request) {
        return null;
    }

    @Override
    public String confirmToken(String token) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
