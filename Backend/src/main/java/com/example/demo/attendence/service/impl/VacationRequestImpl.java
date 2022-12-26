package com.example.demo.attendence.service.impl;

import com.example.demo.attendence.entity.VacationRequest;
import com.example.demo.attendence.mapper.VacationRequestMapper;
import com.example.demo.attendence.model.VacationRequestModel;
import com.example.demo.attendence.repository.VacationRequestRepository;
import com.example.demo.attendence.service.VacationRequestService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacationRequestImpl implements VacationRequestService {

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    private VacationRequestMapper vacationRequestMapper= Mappers.getMapper(VacationRequestMapper.class);

    @Override
    public void requestVacation(Long id, VacationRequestModel vacationRequestModel) {
        VacationRequest vacationRequest= vacationRequestMapper.vacationModelToVactionEntity(vacationRequestModel);
        vacationRequestRepository.save(vacationRequest);
    }
}
