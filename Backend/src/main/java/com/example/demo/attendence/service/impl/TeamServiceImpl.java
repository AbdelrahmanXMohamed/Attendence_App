package com.example.demo.attendence.service.impl;

import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.exception.*;
import com.example.demo.attendence.mapper.UserMapper;
import com.example.demo.attendence.model.TeamRequestModel;
import com.example.demo.attendence.model.TeamResponseModel;
import com.example.demo.attendence.entity.Team;
import com.example.demo.attendence.mapper.TeamMapper;
import com.example.demo.attendence.model.UserResponseModel;
import com.example.demo.attendence.repository.TeamRepository;
import com.example.demo.attendence.repository.UserRepository;
import com.example.demo.attendence.service.TeamService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public TeamServiceImpl(TeamRepository teamRepository, TeamMapper teamMapper, UserMapper userMapper, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public TeamResponseModel createTeam(TeamRequestModel requestModel){
        User currentLogin=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        requestModel.setManager(currentLogin);
        if(requestModel.getManager() == null) throw new TeamMustHaveManagerException();
        // check if the manager exists
        if (!userRepository.findById(requestModel.getManager().getId()).isPresent()) {
            throw new UserDoesNotExistException();
        }
        // check if the manager is member
        if (userRepository.findById(requestModel.getManager().getId()).get().getTeam() != null){
            throw new MemberCannotManageTeamException();
        }
        return this.teamMapper.toResponseModel(this.teamRepository.save(this.teamMapper.toTeam(requestModel)));
    }

    @Override
    public TeamResponseModel addUserToTeam(Long userId,Long teamId){
        User user = this.userRepository.findById(userId).orElseThrow();
        // handle if the user is in a team
        if(user.getTeam() != null) throw new UserAlreadyIsInTeamException();
        // handle if the user is a manager
        List<Team> team1 = this.teamRepository.findByManager_Id(userId);
        if (team1.size() > 0) throw new UserIsMangerException();
        Team team = this.teamRepository.findById(teamId).orElseThrow();
        user.setTeam(team);
        this.userRepository.save(user);
        return this.teamMapper.toResponseModel(team);
    }

    @Override
    public List<UserResponseModel> getAllTeamUsers(Long teamId) {
        Team team = this.teamRepository.findById(teamId).orElseThrow();
        return team.getUsers()
                .stream()
                .map(userMapper::userToModel)
                .toList();
    }

    @Override
    public TeamResponseModel removeUserFromTeam(Long userId, Long teamId) {
        Team team = this.teamRepository.findById(teamId).orElseThrow();
        User user = this.userRepository.findById(userId).orElseThrow();
        if(user.getTeam() == null) throw new UserHasNoTeamException();
        team.getUsers().remove(user);
        user.setTeam(null);
        this.userRepository.save(user);
        this.teamRepository.save(team);
        return this.teamMapper.toResponseModel(team);
    }

    @Override
    public List<TeamResponseModel> getTeamsOfManager() {
        User currentLogin=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Team> teams = this.teamRepository.findByManager_Id(currentLogin.getId());
        return  teams
                .stream()
                .map(this.teamMapper::toResponseModel)
                .toList();
    }

    @Override
    public TeamResponseModel getTeamById(Long teamId) {
        return this.teamMapper.toResponseModel(this.teamRepository.findById(teamId).orElseThrow());
    }


}

