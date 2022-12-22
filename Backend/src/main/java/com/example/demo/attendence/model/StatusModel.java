package com.example.demo.attendence.model;

import com.example.demo.attendence.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StatusModel {

    private LocalDate day;
    private User user;
    private Short status;
}
