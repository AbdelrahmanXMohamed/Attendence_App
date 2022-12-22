package com.example.demo.attendence.controller;

import com.example.demo.attendence.model.StatusRequestModel;
import com.example.demo.attendence.entity.Status;
import com.example.demo.attendence.services.StatusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
public class StatusController {
    @Autowired
    StatusService statusService;

    @GetMapping
    public Status getStatus() {
        return null;
    }

    @PostMapping
    public Status setStatus(@Valid StatusRequestModel statusRequest) {
        return statusService.setStatus(statusRequest);
    }

}
