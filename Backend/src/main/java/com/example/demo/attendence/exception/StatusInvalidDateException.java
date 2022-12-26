package com.example.demo.attendence.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StatusInvalidDateException extends RuntimeException {
    final String message;

}
