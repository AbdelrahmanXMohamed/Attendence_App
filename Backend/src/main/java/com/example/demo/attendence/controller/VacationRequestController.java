package com.example.demo.attendence.controller;

import com.example.demo.attendence.model.ApproveRequuestModel;
import com.example.demo.attendence.model.VacationModel;
import com.example.demo.attendence.model.VacationRequestModel;
import com.example.demo.attendence.service.VacationRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/vacation")
public class VacationRequestController {

    @Autowired
    private VacationRequestService vacationRequestService;

    @PostMapping("/{id}")
    public void requestVacation(@PathVariable Long id, @Valid @RequestBody VacationRequestModel vacationRequestModel){
        vacationRequestService.requestVacation(id,vacationRequestModel);
    }

    @GetMapping("/{userId}/{teamId}")
    public List<VacationModel> allVacationRequestperTeam(@PathVariable Long userId , @PathVariable Long teamId){
        return vacationRequestService.allVacationRequestPerTeam(userId,teamId);
    }

    @PostMapping("/{userId}/{vacationId}")
    public void approveRequest(@PathVariable Long userId ,@PathVariable Long vacationId,@Valid @RequestBody ApproveRequuestModel approve){
        vacationRequestService.approveRequest(userId,vacationId,approve);
    }
}
