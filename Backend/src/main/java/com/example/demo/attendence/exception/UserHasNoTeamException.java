package com.example.demo.attendence.exception;

public class UserHasNoTeamException extends RuntimeException{

    public UserHasNoTeamException(){
        super("User has no team. Cannot remove it from any team");
    }
}
