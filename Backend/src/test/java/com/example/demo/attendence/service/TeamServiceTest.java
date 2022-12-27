package com.example.demo.attendence.service;

import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.mapper.TeamMapper;
import com.example.demo.attendence.mapper.UserMapper;
import com.example.demo.attendence.model.TeamRequestModel;
import com.example.demo.attendence.model.TeamResponseModel;
import com.example.demo.attendence.repository.TeamRepository;
import com.example.demo.attendence.repository.UserRepository;
import com.example.demo.attendence.service.impl.TeamServiceImpl;
import com.google.common.base.Optional;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TeamServiceTest {
    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserRepository userRepository;


    private TeamMapper teamMapper;
    private UserMapper userMapper;

    private TeamService underTest;


    @BeforeEach
    public void setUp(){
        this.teamMapper = Mappers.getMapper(TeamMapper.class);
        this.userMapper = Mappers.getMapper(UserMapper.class);
        underTest = new TeamServiceImpl(teamRepository,teamMapper,userMapper,userRepository);
    }


    @Test
    public void shouldCreateUserWhenUserRequestIsGiven(){
        // given
        Long id = 1L;
        User manager = new User();
        manager.setId(id);
        TeamRequestModel requestModel = new TeamRequestModel();
        requestModel.setManager(manager);
        Optional<User> returnedUser = Optional.of(manager);
        Mockito.when(this.userRepository.findById(id)).thenReturn(returnedUser.toJavaUtil());
        // when
        TeamResponseModel responseModel = underTest.createTeam(requestModel);
        // then


        
    }




}



