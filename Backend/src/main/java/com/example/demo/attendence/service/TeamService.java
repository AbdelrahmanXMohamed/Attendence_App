package com.example.demo.attendence.service;

import com.example.demo.attendence.model.TeamRequestModel;
import com.example.demo.attendence.model.TeamResponseModel;
import com.example.demo.attendence.model.UserResponseModel;

import java.util.List;

public interface TeamService {
    TeamResponseModel createTeam(TeamRequestModel requestModel);

    TeamResponseModel addUserToTeam(Long userId,Long teamId);

    List<UserResponseModel> getAllTeamUsers(Long teamId);

    TeamResponseModel removeUserFromTeam(Long userId, Long teamId);

    void removeTeam(Long teamId);

}
