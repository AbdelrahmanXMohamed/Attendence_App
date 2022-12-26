package com.example.demo.attendence.exception;

public class UserAlreadyIsInTeamException extends RuntimeException{

    public UserAlreadyIsInTeamException(){
        super("user has joined in team before. You cannot add him to the team again");
    }
}
