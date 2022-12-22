package com.example.demo.attendence.entity;

import com.example.demo.attendence.utils.VacationStatus;
import com.example.demo.attendence.utils.VacationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "vacation_request")
public class VacationRequest {

    @Id
    private Long id;

    private VacationType type;

    private VacationStatus status;

    private LocalDate stratTime;

    private LocalDate endTime;

    private String comment;

    private Long numberOfDays;
}
