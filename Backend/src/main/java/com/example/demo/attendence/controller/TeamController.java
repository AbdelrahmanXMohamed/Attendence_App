package com.example.demo.attendence.controller;

import com.example.demo.attendence.model.TeamRequestModel;
import com.example.demo.attendence.model.TeamResponseModel;
import com.example.demo.attendence.service.impl.TeamServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("teams")
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

    @PostMapping("{teamId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public TeamResponseModel addUserToTeam(@PathVariable Long teamId,@PathVariable Long userId){
        return this.teamService.addUserToTeam(userId,teamId);
    }
    @GetMapping("/users/{teamId}")
    public List<User> getAllTeamUsers(@PathVariable Long teamId){
        return this.teamService.getAllTeamUsers(teamId);
    }
    @PutMapping("{teamId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public TeamResponseModel removeUserFromTeam(@PathVariable Long teamId,@PathVariable Long userId){
        return this.teamService.removeUserFromTeam(userId,teamId);
    }

    @DeleteMapping("{teamId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeTeam(@PathVariable Long teamId){
        this.teamService.removeTeam(teamId);
    }


}
