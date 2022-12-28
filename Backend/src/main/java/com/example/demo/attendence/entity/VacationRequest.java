package com.example.demo.attendence.entity;

import com.example.demo.attendence.utils.VacationStatus;
import com.example.demo.attendence.utils.VacationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "vacation_requests")
@Getter
@Setter
public class VacationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private VacationType type;

    private VacationStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    private String comment;

    private int numberOfDays ;

    @ManyToOne
    @JoinColumn(name ="user_id",referencedColumnName = "id")
    @JsonIgnore
    private User user;

}