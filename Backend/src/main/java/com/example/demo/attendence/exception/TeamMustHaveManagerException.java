package com.example.demo.attendence.exception;

public class TeamMustHaveManagerException extends RuntimeException{

    public TeamMustHaveManagerException(){
        super("Cannot Create Team Without Manager");
    }
}
