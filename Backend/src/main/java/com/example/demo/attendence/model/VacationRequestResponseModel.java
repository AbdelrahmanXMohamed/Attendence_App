package com.example.demo.attendence.model;

import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.utils.VacationStatus;
import com.example.demo.attendence.utils.VacationType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VacationRequestResponseModel {

    private Long id;
    private VacationType type;

    private VacationStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    private String comment;

    private int numberOfDays ;

    private UserResponseModel user;

}
