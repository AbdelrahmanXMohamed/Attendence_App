package com.example.demo.attendence.controller;

import com.example.demo.attendence.model.VacationRequestModel;
import com.example.demo.attendence.service.VacationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vacation")
public class VacationRequestController {

    @Autowired
    private VacationRequestService vacationRequestService;

    @PostMapping("/{id}")
    public void requestVacation(@PathVariable Long id, @RequestBody VacationRequestModel vacationRequestModel){
        vacationRequestService.requestVacation(id,vacationRequestModel);
    }
}
