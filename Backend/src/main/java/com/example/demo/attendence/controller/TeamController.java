package com.example.demo.attendence.controller;

import com.example.demo.attendence.model.TeamRequestModel;
import com.example.demo.attendence.model.TeamResponseModel;
import com.example.demo.attendence.model.UserResponseModel;
import com.example.demo.attendence.service.impl.TeamServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("teams")
@CrossOrigin("http://localhost:4200")
public class TeamController {

    private final TeamServiceImpl teamService;

    public TeamController(TeamServiceImpl teamService) {
        this.teamService = teamService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponseModel createTeam(@RequestBody TeamRequestModel requestModel){
        return this.teamService.createTeam(requestModel);
    }
    @PostMapping("{teamId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public TeamResponseModel addUserToTeam(@PathVariable Long teamId,@PathVariable Long userId){
        return this.teamService.addUserToTeam(userId,teamId);
    }
    @GetMapping("/users/{teamId}")
    public List<UserResponseModel> getAllTeamUsers(@PathVariable Long teamId){
        return this.teamService.getAllTeamUsers(teamId);
    }
    @DeleteMapping("{teamId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public TeamResponseModel removeUserFromTeam(@PathVariable Long teamId,@PathVariable Long userId){
        return this.teamService.removeUserFromTeam(userId,teamId);
    }

    @GetMapping("/manager/{managerId}")
    public List<TeamResponseModel> getTeamsOfManager(@PathVariable Long managerId){
        return this.teamService.getTeamsOfManager(managerId);
    }

    @GetMapping("{teamId}")
    public TeamResponseModel getTeamById(@PathVariable Long teamId){
        return this.teamService.getTeamById(teamId);
    }

}
