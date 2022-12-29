package com.example.demo.attendence.service.impl;

import com.example.demo.attendence.entity.Team;
import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.entity.VacationRequest;
import com.example.demo.attendence.exception.*;
import com.example.demo.attendence.mapper.VacaionRequestMapper;
import com.example.demo.attendence.model.*;
import com.example.demo.attendence.repository.TeamRepository;
import com.example.demo.attendence.repository.UserRepository;
import com.example.demo.attendence.repository.VacationRequestRepository;
import com.example.demo.attendence.service.StatusService;
import com.example.demo.attendence.service.VacationRequestService;
import com.example.demo.attendence.utils.DailyStatus;
import com.example.demo.attendence.utils.VacationStatus;
import com.example.demo.attendence.utils.VacationType;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VacationRequestImpl implements VacationRequestService {

    private final VacationRequestRepository vacationRequestRepo;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private VacaionRequestMapper vacaionRequestMapper;
    private final StatusService statusService;

    public VacationRequestImpl(VacationRequestRepository vacationRequestRepo, UserRepository userRepository, TeamRepository teamRepository, VacaionRequestMapper vacaionRequestMapper, StatusService statusService) {
        this.vacationRequestRepo = vacationRequestRepo;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.vacaionRequestMapper = vacaionRequestMapper;
        this.statusService = statusService;
    }

    @Override
    public void addRequestVacation(Long id, VacationRequestModel vacationRequestModel) {
        User user =userRepository.findById(id).orElseThrow(UserDoesNotExistException::new);
        if (user.getTeam()==null) {
            throw new UserCannotTakeVacationException();
        }

       VacationRequest vacationRequest =vacaionRequestMapper.vacationRequestModelToEntity(vacationRequestModel);

        if(vacationRequest.getType().equals( VacationType.CASUAL)) {
            vacationRequest.setStatus(VacationStatus.ACCEPT);
        }
        else {
            vacationRequest.setStatus(VacationStatus.PENDING);
        }

        vacationRequest.setUser(user);
        System.out.println(vacationRequest);
        vacationRequestRepo.save(vacationRequest);
    }

    @Override
    public List<VacationModel> getAllVacationRequestPerTeam(Long userId, Long teamId) {
        userRepository.findById(userId).orElseThrow(UserDoesNotExistException::new);
        Team team =teamRepository.findById(teamId).orElseThrow(TeamDoesNotExistException::new);
        if(!team.getManager().getId().equals(userId)) {
            throw new NotTeamManagerException();
        }

        List<VacationRequest> vacationRequestList =vacationRequestRepo.getAllVacationRequestPerTeam(teamId);
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
        userRepository.findById(userId).orElseThrow(UserDoesNotExistException::new);

        VacationRequest vacationRequest =vacationRequestRepo.findById(vacationId).orElseThrow(VacationNotExistException::new);

        if(!vacationRequest.getUser().getTeam().getManager().getId().equals(userId)) {
            throw new NotTeamManagerException();
        }

        if (!vacationRequest.getStatus().equals(VacationStatus.PENDING)) {
            throw new VacationApproveException();
        }

        vacationRequest.setStatus(VacationStatus.values()[approveRequuestModel.getApprove()]);
        vacationRequestRepo.save(vacationRequest);

        if (vacationRequest.getStatus().equals(VacationStatus.ACCEPT)){
            setStatus(vacationRequest);
        }
    }

    @Override
    public List<VacationRequestResponseModel> getAllVacationRequestPerUser(Long userId) {
        userRepository.findById(userId).orElseThrow(UserDoesNotExistException::new);
        List<VacationRequestResponseModel> vacationRequestResponseModels=new ArrayList<>();
        List<VacationRequest> vacationRequests=vacationRequestRepo.getAllVacationRequestPerUser(userId);
        vacationRequests.forEach(v->{
            vacationRequestResponseModels.add(vacaionRequestMapper.entityToVacationResponseModel(v));
        });

        return vacationRequestResponseModels;
    }

    public void setStatus(@NotNull VacationRequest vacationRequest){
        StatusRequestModel statusRequestModel =new StatusRequestModel();
        statusRequestModel.setUserId(vacationRequest.getUser().getId());
        statusRequestModel.setStatus(DailyStatus.ABSENCE);

        for (int i =0; i<vacationRequest.getNumberOfDays(); i++) {
            statusRequestModel.setDay(vacationRequest.getStartDate().plusDays(i));
            statusService.setStatus(statusRequestModel);
        }
    }


}
