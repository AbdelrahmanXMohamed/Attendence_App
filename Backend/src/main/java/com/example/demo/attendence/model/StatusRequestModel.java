package com.example.demo.attendence.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusRequestModel {

    @NotBlank(message = "Day is required")
    private LocalDate day;
    @NotBlank(message = "User id is required")
    private Long userId;
    @NotBlank(message = "Status code is required")
    @Min(value = 0, message = "Can't be less then zero")
    @Max(value = 3, message = "Can't greater than 3")
    private Short status;

}
