package com.example.demo.attendence.model;

import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.utils.DailyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusModel {
    private LocalDate day;
    private User user;
    private DailyStatus status;
}
