package com.example.demo.attendence.model;

import com.example.demo.attendence.utils.VacationStatus;
import com.example.demo.attendence.utils.VacationType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public class VacationRequestModel {

   @NotNull
    private VacationType type;

    private VacationStatus status;

    private LocalDate stratTime;

    private LocalDate endTime;

    private String comment;
}
