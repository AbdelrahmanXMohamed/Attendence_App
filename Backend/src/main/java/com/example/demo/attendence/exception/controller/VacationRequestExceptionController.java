package com.example.demo.attendence.exception.controller;

import com.example.demo.attendence.exception.UserCannotTakeVacationException;
import com.example.demo.attendence.exception.VacationApproveException;
import com.example.demo.attendence.exception.VacationNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class VacationRequestExceptionController {

    @ExceptionHandler(value = VacationNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notTeamManager() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Vacation doesn't exits ");
        return response;
    }

    @ExceptionHandler(value = VacationApproveException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> vacationApprove() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Vacation was approved. ");
        return response;
    }

    @ExceptionHandler(value = UserCannotTakeVacationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> userCannotTakeVacationException() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "You cannot take vacation, you not in team yet. ");
        return response;
    }

}
