package com.example.demo.attendence.entity;

import com.example.demo.attendence.utils.VacationStatus;
import com.example.demo.attendence.utils.VacationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "vacation_request")
@Data
public class VacationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private VacationType type;

    private VacationStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    private String comment;

    private Long numberOfDays ;

    @ManyToOne
    @JoinColumn(name ="user_id",referencedColumnName = "id")
    @JsonIgnore
    private User user;

}