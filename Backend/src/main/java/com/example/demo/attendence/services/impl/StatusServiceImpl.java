package com.example.demo.attendence.services.impl;

import com.example.demo.attendence.entity.Status;
import com.example.demo.attendence.exception.StatusInvalidDateException;
import com.example.demo.attendence.exception.UserDoesNotExistException;
import com.example.demo.attendence.model.StatusModel;
import com.example.demo.attendence.model.StatusRequestModel;
import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.mapper.StatusMapper;
import com.example.demo.attendence.repository.StatusRepository;
import com.example.demo.attendence.repository.UserRepository;
import com.example.demo.attendence.services.StatusService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    StatusRepository statusRepository;
    @Autowired
    UserRepository userRepository;
    StatusMapper statusMapper = Mappers.getMapper(StatusMapper.class);

    @Override
    public Status setStatus(StatusRequestModel statusRequest) {
        Optional<User> user = userRepository.findById(statusRequest.getUserId());
        user.orElseThrow(UserDoesNotExistException::new);
        if (!statusRequest.getDay().isAfter(LocalDate.now())) {
            throw new StatusInvalidDateException();
        }
        StatusModel statusModel = statusMapper.statusRequestModelToStatusModel(statusRequest);
        statusModel.setUser(user.get());
        return statusRepository.save(statusMapper.statusModelToStatus(statusModel));
    }
}
