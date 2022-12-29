package com.example.demo.attendence.mapper;

import com.example.demo.attendence.entity.VacationRequest;
import com.example.demo.attendence.model.VacationModel;
import com.example.demo.attendence.model.VacationRequestModel;
import com.example.demo.attendence.model.VacationRequestResponseModel;
import com.example.demo.attendence.utils.VacationType;
import org.mapstruct.Mapper;

@Mapper
public interface VacaionRequestMapper {


    public VacationRequestResponseModel entityToVacationResponseModel(VacationRequest vacationRequest);


    default VacationModel toVacationModel(VacationRequest vacationRequest){
        VacationModel vacationModel=new VacationModel();
        vacationModel.setUsername(vacationRequest.getUser().getUsername());
        vacationModel.setStratDate(vacationRequest.getStartDate());
        vacationModel.setUserId(vacationRequest.getUser().getId());
        vacationModel.setNumberOfDay(vacationRequest.getNumberOfDays());
        vacationModel.setComment(vacationRequest.getComment());
        vacationModel.setEndDate(vacationRequest.getEndDate());
        vacationModel.setType(vacationRequest.getType());
        return vacationModel;
    }

    default VacationRequest vacationRequestModelToEntity(VacationRequestModel vacationRequestModel){
        VacationRequest vacationRequest=new VacationRequest();
        vacationRequest.setType(vacationRequestModel.getType());
        vacationRequest.setStartDate(vacationRequestModel.getStratDate());
        if(vacationRequest.getType()== VacationType.CASUAL)
            vacationRequest.setEndDate(vacationRequest.getStartDate().plusDays(1));
        else
            vacationRequest.setEndDate(vacationRequestModel.getEndDate());

       vacationRequest.setComment(vacationRequestModel.getComment());
        vacationRequest.setNumberOfDays( vacationRequest.getEndDate().getDayOfMonth()-vacationRequest.getStartDate().getDayOfMonth());
        return vacationRequest;
    }


}
