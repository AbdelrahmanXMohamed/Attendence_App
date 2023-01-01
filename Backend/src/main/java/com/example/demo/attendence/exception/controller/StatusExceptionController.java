package com.example.demo.attendence.exception.controller;

import com.example.demo.attendence.exception.StatusAccessIsForbiddenException;
import com.example.demo.attendence.exception.StatusCanNotBeChangedException;
import com.example.demo.attendence.exception.StatusInvalidDateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class StatusExceptionController {
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return response;
    }

    @ExceptionHandler(value = StatusInvalidDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> statusStarterDateAfterEnderDateException(StatusInvalidDateException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return response;
    }

    @ExceptionHandler(value = StatusAccessIsForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> statusAccessIsForbiddenException() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Status access is forbidden");
        return response;
    }
    @ExceptionHandler(value = StatusCanNotBeChangedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> statusCanNotBeChangedException() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Status can not be changed");
        return response;
    }
}
