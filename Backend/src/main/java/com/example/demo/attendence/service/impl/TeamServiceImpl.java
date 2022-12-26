package com.example.demo.attendence.service.impl;

import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.model.TeamRequestModel;
import com.example.demo.attendence.model.TeamResponseModel;
import com.example.demo.attendence.entity.Team;
import com.example.demo.attendence.mapper.TeamMapper;
import com.example.demo.attendence.repository.TeamRepository;
import com.example.demo.attendence.repository.UserRepository;
import com.example.demo.attendence.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {



    private final TeamRepository teamRepository;
    private final TeamMapper mapper;
    private final UserRepository userRepository;

    public TeamServiceImpl(TeamRepository teamRepository, TeamMapper mapper, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public TeamResponseModel createTeam(TeamRequestModel requestModel){
        return this.mapper.toResponseModel(this.teamRepository.save(this.mapper.toTeam(requestModel)));
    }

    public TeamResponseModel addUserToTeam(Long userId,Long teamId){
        User user = this.userRepository.findById(userId).orElseThrow();
        Team team = this.teamRepository.findById(teamId).orElseThrow();
        team.getUsers().add(user);
        this.teamRepository.save(team);
        user.setTeam(team);
        this.userRepository.save(user);
        return this.mapper.toResponseModel(team);
    }

    public List<User> getAllTeamUsers(Long teamId) {
        Team team = this.teamRepository.findById(teamId).orElseThrow();
        return team.getUsers();
    }

    public TeamResponseModel removeUserFromTeam(Long userId, Long teamId) {
        Team team = this.teamRepository.findById(teamId).orElseThrow();
        User user = this.userRepository.findById(userId).orElseThrow();
        team.getUsers().remove(user);
        user.setTeam(null);
        this.userRepository.save(user);
        this.teamRepository.save(team);
        return this.mapper.toResponseModel(team);
    }

    public void removeTeam(Long teamId) {
        this.teamRepository.deleteById(teamId);
    }

    public TeamResponseModel getById(Long id) {
        return this.mapper.toResponseModel(this.teamRepository.findById(id).orElseThrow());
    }
}

