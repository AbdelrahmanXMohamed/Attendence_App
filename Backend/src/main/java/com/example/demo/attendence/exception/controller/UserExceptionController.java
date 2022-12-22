package com.example.demo.attendence.exception.controller;

import com.example.demo.attendence.exception.UserDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserExceptionController {
    @ExceptionHandler(value = UserDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> userDoesnotExistException() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "User doesn't exits");
        return response;
    }


}
