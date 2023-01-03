package com.example.demo.attendence.controller;

import com.example.demo.attendence.model.ApproveRequuestModel;
import com.example.demo.attendence.model.VacationModel;
import com.example.demo.attendence.model.VacationRequestModel;
import com.example.demo.attendence.model.VacationRequestResponseModel;
import com.example.demo.attendence.service.VacationRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/vacations")
@CrossOrigin("http://localhost:4200")
public class VacationRequestController {

    @Autowired
    private VacationRequestService vacationRequestService;

    @PostMapping
    public void createRequestVacation( @Valid @RequestBody VacationRequestModel vacationRequestModel){
        vacationRequestService.addRequestVacation(vacationRequestModel);
    }

    @GetMapping("/{teamId}")
    public List<VacationModel> getAllVacationRequestperTeam(  @PathVariable Long teamId){
        return vacationRequestService.getAllVacationRequestPerTeam(teamId);
    }

    @PostMapping("/{vacationId}")
    public void approveRequest( @PathVariable Long vacationId,@Valid @RequestBody ApproveRequuestModel approve){
        vacationRequestService.approveRequest(vacationId,approve);
    }
    @GetMapping
    public List<VacationRequestResponseModel> getAllVacationRequestperUser(){
        return vacationRequestService.getAllVacationRequestPerUser();
    }



}
