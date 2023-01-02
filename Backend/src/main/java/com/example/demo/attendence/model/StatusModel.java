package com.example.demo.attendence.model;

import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.utils.DailyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class StatusModel {
    private LocalDate day;
    private UserResponseModel user;
    private DailyStatus status;
}
