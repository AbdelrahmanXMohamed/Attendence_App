package com.example.demo.attendence.exception.controller;

import com.example.demo.attendence.exception.TeamDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class TeamExceptionController {
    @ExceptionHandler(value = TeamDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> teamDoesNotExistException() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Team doesn't exits");
        return response;
    }
}
