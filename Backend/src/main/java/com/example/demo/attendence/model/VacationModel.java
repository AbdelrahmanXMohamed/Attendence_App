package com.example.demo.attendence.model;

import com.example.demo.attendence.utils.VacationType;
import lombok.Data;

import java.time.LocalDate;
@Data
public class VacationModel {


    private Long id;
    private Long userId;

    private String username;

    private VacationType type;

    private LocalDate startDate;

    private LocalDate endDate;

    private int numberOfDay;

    private String comment;
}
