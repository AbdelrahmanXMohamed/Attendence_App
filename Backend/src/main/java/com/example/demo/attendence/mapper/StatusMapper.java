package com.example.demo.attendence.mapper;

import com.example.demo.attendence.entity.Status;
import com.example.demo.attendence.model.StatusModel;
import com.example.demo.attendence.model.StatusRequestModel;
import org.mapstruct.Mapper;

@Mapper
public interface StatusMapper {
    StatusModel statusRequestModelToStatusModel(StatusRequestModel statusRequestModel);

    Status statusModelToStatus(StatusModel statusModel);

}
