package com.example.demo.attendence.model;

import com.example.demo.attendence.utils.VacationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDate;

@Data
public class VacationRequestModel {

    @NotNull(message = "Type of vacation request mustn't be null.")
    private VacationType type;

   @NotNull(message = "Start date mustn't be null.")
    private LocalDate stratDate;

    private LocalDate endDate=LocalDate.now();

    private String comment;
}
