package com.example.demo.attendence.service;

import com.example.demo.attendence.model.ApproveRequuestModel;
import com.example.demo.attendence.model.VacationModel;
import com.example.demo.attendence.model.VacationRequestModel;

import java.util.List;

public interface VacationRequestService {

    public void requestVacation(Long id, VacationRequestModel vacationRequestModel);

    public List<VacationModel> allVacationRequestPerTeam(Long userId , Long teamId);

    public void approveRequest(Long userId, Long vacationId, ApproveRequuestModel approveRequuestModel);


}
