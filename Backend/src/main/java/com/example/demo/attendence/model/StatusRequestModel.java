package com.example.demo.attendence.model;

import com.example.demo.attendence.annotation.EnumNamePattern;
import com.example.demo.attendence.utils.DailyStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class StatusRequestModel {
    @NotNull(message = "Day is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate day;
    @NotNull(message = "User id is required")
    @Positive(message = "Invalid id")
    private Long userId;
    @NotNull(message = "Status can't empty")
    @EnumNamePattern(regexp = "ABSENCE|REMOTE|ONSITE")
    private DailyStatus status;
}
