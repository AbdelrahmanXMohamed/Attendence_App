package com.example.demo.attendence.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusBetweenTwoDateRequestModel {
    @NotNull(message = "Starter date is required")
    LocalDate starterDate;
    @NotNull(message = "Ender date is required")
    LocalDate enderDate;
}
