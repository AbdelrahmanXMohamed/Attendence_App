package com.example.demo.attendence.controller;

import com.example.demo.attendence.model.StatusBetweenTwoDateRequestModel;
import com.example.demo.attendence.model.StatusModel;
import com.example.demo.attendence.model.StatusRequestModel;
import com.example.demo.attendence.entity.Status;
import com.example.demo.attendence.service.StatusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/status")
@CrossOrigin("http://localhost:4200")
public class StatusController {
    @Autowired
    StatusService statusService;

    @GetMapping
    public Status getStatus() {
        return null;
    }

    @PostMapping
    public Status setStatus(@Valid @RequestBody StatusRequestModel statusRequest) {
        return statusService.setStatus(statusRequest);
    }

    @GetMapping("/users-report/{id}")
    public List<StatusModel> getReportForUser(@PathVariable Long id, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return statusService.getReportForUser(id, new StatusBetweenTwoDateRequestModel(startDate, endDate));
    }

    @GetMapping("/report-per-day-for-users/{userId}")
    public StatusModel getReportPerDayForUser(@PathVariable Long userId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return statusService.getReportPerDayForUser(userId, day);
    }

    @GetMapping("/report-per-day-for-teams/{teamId}")
    public List<StatusModel> getReportPerDayForTeam(@PathVariable Long teamId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return statusService.getReportPerDayForTeam(teamId, day);
    }

    @GetMapping("/report-for-current-week-for-users/{userId}")
    public List<StatusModel> getReportForCurrentWeekForUser(@PathVariable Long userId) {
        return statusService.getReportForCurrentWeekForUser(userId);
    }

    @GetMapping("/report-for-current-week-for-teams/{teamId}")
    public List<StatusModel> getReportForCurrentWeekForTeam(@PathVariable Long teamId) {
        return statusService.getReportForCurrentWeekForTeam(teamId);
    }
}
