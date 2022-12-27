package com.example.demo.attendence.service;

import com.example.demo.attendence.entity.Status;
import com.example.demo.attendence.model.StatusBetweenTwoDateRequestModel;
import com.example.demo.attendence.model.StatusModel;
import com.example.demo.attendence.model.StatusRequestModel;

import java.time.LocalDate;
import java.util.List;

public interface StatusService {


    Status setStatus(StatusRequestModel statusRequest);

    List<StatusModel> getReportForUser(Long id, StatusBetweenTwoDateRequestModel statusBetweenTwoDateRequestModel);

    StatusModel getReportPerDayForUser(Long userId, LocalDate date);

    List<StatusModel> getReportPerDayForTeam(Long teamId, LocalDate date);

    List<StatusModel> getReportForCurrentWeekForUser(Long userId);

    List<StatusModel> getReportForCurrentWeekForTeam(Long teamId);
}


