package com.example.demo.attendence.mapper;


import com.example.demo.attendence.model.TeamRequestModel;
import com.example.demo.attendence.model.TeamResponseModel;
import com.example.demo.attendence.entity.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper {



    Team toTeam (TeamRequestModel requestModel);


    TeamResponseModel toResponseModel(Team team);
}
