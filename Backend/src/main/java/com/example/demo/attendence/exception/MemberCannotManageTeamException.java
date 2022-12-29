package com.example.demo.attendence.exception;

public class MemberCannotManageTeamException extends RuntimeException{

    public MemberCannotManageTeamException(){
        super("The Manager You Provided is a member in other team. cannot be manager");
    }
}
