package com.example.demo.attendence.exception.controller;

import com.example.demo.attendence.exception.StatusInvalidDateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class StatusExceptionController {
    @ExceptionHandler(value = StatusInvalidDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> userDoesnotExistException() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Date must be up coming date");
        return response;
    }
}
