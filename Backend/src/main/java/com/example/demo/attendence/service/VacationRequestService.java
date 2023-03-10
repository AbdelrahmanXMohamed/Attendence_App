package com.example.demo.attendence.service;

import com.example.demo.attendence.model.ApproveRequuestModel;
import com.example.demo.attendence.model.VacationModel;
import com.example.demo.attendence.model.VacationRequestModel;
import com.example.demo.attendence.model.VacationRequestResponseModel;

import java.util.List;

public interface VacationRequestService {

    public void addRequestVacation( VacationRequestModel vacationRequestModel);

    public List<VacationModel> getAllVacationRequestPerTeam( Long teamId);

    public void approveRequest( Long vacationId, ApproveRequuestModel approveRequuestModel);

    public List<VacationRequestResponseModel> getAllVacationRequestPerUser();


}
