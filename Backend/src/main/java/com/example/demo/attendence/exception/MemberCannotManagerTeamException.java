package com.example.demo.attendence.exception;

public class MemberCannotManagerTeamException extends RuntimeException{

    public MemberCannotManagerTeamException(){
        super("The Manager You Provided is a member in other team. cannot be manager");
    }
}
