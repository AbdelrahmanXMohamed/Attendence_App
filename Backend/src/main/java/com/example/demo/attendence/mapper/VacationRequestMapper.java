package com.example.demo.attendence.mapper;

import com.example.demo.attendence.entity.VacationRequest;
import com.example.demo.attendence.model.VacationRequestModel;
import org.mapstruct.Mapper;

@Mapper
public interface VacationRequestMapper {

    public VacationRequest vacationModelToVactionEntity(VacationRequestModel vacationRequestModel);
}
