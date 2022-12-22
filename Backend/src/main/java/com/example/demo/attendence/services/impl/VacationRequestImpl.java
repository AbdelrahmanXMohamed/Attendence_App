package com.example.demo.attendence.services.impl;

import com.example.demo.attendence.entity.VacationRequest;
import com.example.demo.attendence.mapper.VacationRequestMapper;
import com.example.demo.attendence.model.VacationRequestModel;
import com.example.demo.attendence.repository.VacationRequestRepo;
import com.example.demo.attendence.services.VacationRequestService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacationRequestImpl implements VacationRequestService {

    @Autowired
    private VacationRequestRepo vacationRequestRepo;

    private VacationRequestMapper vacationRequestMapper= Mappers.getMapper(VacationRequestMapper.class);

    @Override
    public void requestVacation(Long id, VacationRequestModel vacationRequestModel) {
        VacationRequest vacationRequest= vacationRequestMapper.vacationModelToVactionEntity(vacationRequestModel);
        vacationRequestRepo.save(vacationRequest);
    }
}
