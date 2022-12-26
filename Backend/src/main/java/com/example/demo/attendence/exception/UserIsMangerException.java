package com.example.demo.attendence.exception;

public class UserIsMangerException extends RuntimeException{
    public UserIsMangerException(){
        super("User Is A Manager Cannot Be Added To Team.");
    };
}
