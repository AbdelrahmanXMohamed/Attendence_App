package com.example.demo.attendence.exception.controller;

import com.example.demo.attendence.exception.NotTeamManagerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TeamExecptionController {


    @ExceptionHandler(value = NotTeamManagerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notTeamManager() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "you can't access this team ");
        return response;
    }
}
