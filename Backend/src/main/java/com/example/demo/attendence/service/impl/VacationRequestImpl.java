package com.example.demo.attendence.service.impl;

import com.example.demo.attendence.entity.Team;
import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.entity.VacationRequest;
import com.example.demo.attendence.exception.*;
import com.example.demo.attendence.mapper.VacaionRequestMapper;
import com.example.demo.attendence.model.ApproveRequuestModel;
import com.example.demo.attendence.model.VacationModel;
import com.example.demo.attendence.model.VacationRequestModel;
import com.example.demo.attendence.repository.TeamRepository;
import com.example.demo.attendence.repository.UserRepository;
import com.example.demo.attendence.repository.VacationRequestRepository;
import com.example.demo.attendence.service.VacationRequestService;
import com.example.demo.attendence.utils.VacationStatus;
import com.example.demo.attendence.utils.VacationType;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VacationRequestImpl implements VacationRequestService {

    @Autowired
    private final VacationRequestRepository vacationRequestRepo;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TeamRepository teamRepository;

    private VacaionRequestMapper vacaionRequestMapper;

    public VacationRequestImpl(VacationRequestRepository vacationRequestRepo, UserRepository userRepository, TeamRepository teamRepository, VacaionRequestMapper vacaionRequestMapper) {
        this.vacationRequestRepo = vacationRequestRepo;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.vacaionRequestMapper = vacaionRequestMapper;
    }

    @Override
    public void requestVacation(Long id, VacationRequestModel vacationRequestModel) {
        Optional<User> user =userRepository.findById(id);
        user.orElseThrow(UserDoesNotExistException::new);
       VacationRequest vacationRequest =vacaionRequestMapper.vacationRequestModelToEntity(vacationRequestModel);

        if(vacationRequest.getType().equals( VacationType.CASUAL))
            vacationRequest.setStatus(VacationStatus.ACCEPT);
        else
            vacationRequest.setStatus(VacationStatus.PENDING);

        vacationRequest.setUser(user.get());
        vacationRequestRepo.save(vacationRequest);
    }

    @Override
    public List<VacationModel> allVacationRequestPerTeam(Long userId, Long teamId) {
        Optional<User> user =userRepository.findById(userId);
        user.orElseThrow(UserDoesNotExistException::new);
        Optional<Team> team =teamRepository.findById(teamId);
        team.orElseThrow(TeamDoesNotException::new);
        if(!team.get().getManager().getId().equals(userId))
            throw new NotTeamManager();

        List<VacationRequest> vacationRequestList =vacationRequestRepo.findAll();
        List<VacationModel> vacationModels=new ArrayList<>();
        vacationRequestList.forEach(v->{
            if(v.getUser().getTeam().getId().equals(teamId)){
                vacationModels.add(vacaionRequestMapper.toVacationModel(v));
            }
        });

        return vacationModels;
    }

    @Override
    public void approveRequest(Long userId, Long vacationId,  ApproveRequuestModel approveRequuestModel) {
        Optional<User> user =userRepository.findById(userId);
        user.orElseThrow(UserDoesNotExistException::new);

        Optional<VacationRequest> vacationRequest =vacationRequestRepo.findById(vacationId);
        vacationRequest.orElseThrow(VacationNotExistException::new);

        if(!vacationRequest.get().getUser().getTeam().getManager().getId().equals(userId))
            throw new NotTeamManager();

        if (!vacationRequest.get().getStatus().equals(VacationStatus.PENDING))
            throw new VacationApprove();


        vacationRequest.get().setStatus(VacationStatus.values()[approveRequuestModel.getApprove()]);
          vacationRequestRepo.save(vacationRequest.get());
    }
}
