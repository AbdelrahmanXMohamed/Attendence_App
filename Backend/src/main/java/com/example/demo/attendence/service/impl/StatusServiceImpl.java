package com.example.demo.attendence.service.impl;

import com.example.demo.attendence.entity.Status;
import com.example.demo.attendence.entity.Team;
import com.example.demo.attendence.exception.*;
import com.example.demo.attendence.mapper.UserMapper;
import com.example.demo.attendence.model.StatusBetweenTwoDateRequestModel;
import com.example.demo.attendence.model.StatusModel;
import com.example.demo.attendence.model.StatusRequestModel;
import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.mapper.StatusMapper;
import com.example.demo.attendence.service.StatusService;
import com.example.demo.attendence.repository.StatusRepository;
import com.example.demo.attendence.repository.TeamRepository;
import com.example.demo.attendence.repository.UserRepository;
import com.example.demo.attendence.utils.DailyStatus;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class StatusServiceImpl implements StatusService {
    final
    StatusRepository statusRepository;
    final
    UserRepository userRepository;
    final
    TeamRepository teamRepository;
    final StatusMapper statusMapper =
            Mappers.getMapper(StatusMapper.class);
    final UserMapper userMapper =
            Mappers.getMapper(UserMapper.class);

    public StatusServiceImpl(StatusRepository statusRepository, UserRepository userRepository, TeamRepository teamRepository) {
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public Status setStatus(StatusRequestModel statusRequest) {
        User user = userRepository.findById(statusRequest.getUserId())
                .orElse(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        if (statusRequest.getStatus().equals(DailyStatus.VACATION)) {
            userManagerStatusGuard(user.getId());
        }
        if (!statusRequest.getDay().equals(LocalDate.now()) && !statusRequest.getStatus().equals(DailyStatus.VACATION)) {
            throw new StatusInvalidDateException("Date must be current date only");
        }
        if (statusRepository.findByUserAndDate(statusRequest.getUserId(), statusRequest.getDay()).isPresent())
        {
            throw new StatusCanNotBeChangedException();
        }
        StatusModel statusModel = statusMapper.statusRequestModelToStatusModel(statusRequest);
        statusModel.setUser(userMapper.userToModel(user));
        return statusRepository.save(statusMapper.statusModelToStatus(statusModel));
    }

    @Override
    public StatusModel todayStatus() {
        User currentLoginUser = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return statusMapper.statusToStatusModel(statusRepository.findByUserAndDate(currentLoginUser.getId(),LocalDate.now())
                .orElse(new Status(0L,LocalDate.now(),currentLoginUser,DailyStatus.ABSENCE)));

    }

    @Override
    public List<StatusModel> getReportForUser(Long userId, StatusBetweenTwoDateRequestModel statusBetweenTwoDateRequestModel) {
        userManagerStatusGuard(userId);
        if (isValidRange(statusBetweenTwoDateRequestModel)) {
            throw new StatusInvalidDateException("Start date can't be after end date");
        }
        List<StatusModel> result = new ArrayList<>();
        for (LocalDate starter = statusBetweenTwoDateRequestModel.getStarterDate(); isValidStarter(statusBetweenTwoDateRequestModel, starter); starter = starter.plusDays(1)) {
            result.add(getStatusForUser(userId, starter));
        }
        return result;
    }

    @Override
    public StatusModel getReportPerDayForUser(Long userId, LocalDate date) {
        userManagerStatusGuard(userId);
        return getStatusForUser(userId, date);
    }

    @Override
    public List<StatusModel> getReportPerDayForTeam(Long teamId, LocalDate date) {
        teamStatusGuard(teamId);
        Team team = teamRepository.getReferenceById(teamId);
        return team.getUsers().stream().map(user -> getStatusForUser(user.getId(), date)).toList();
    }

    @Override
    public List<StatusModel> getReportForCurrentWeekForUser(Long userId) {
        userManagerStatusGuard(userId);
        List<LocalDate> allDaysOfWeek = Arrays.stream(DayOfWeek.values()).map(LocalDate.now()::with).toList();
        return allDaysOfWeek.stream().map(day -> getStatusForUser(userId, day)).toList();
    }

    @Override
    public List<StatusModel> getReportForCurrentWeekForTeam(Long teamId) {
        teamStatusGuard(teamId);
        Team team = teamRepository.getReferenceById(teamId);
        List<LocalDate> allDaysOfWeek = Arrays.stream(DayOfWeek.values()).map(LocalDate.now()::with).toList();
        return team
                .getUsers()
                .stream()
                .flatMap(user ->
                        allDaysOfWeek
                                .stream()
                                .map(day -> getStatusForUser(user.getId(), day)))
                .toList();
    }

    private static boolean isValidStarter(StatusBetweenTwoDateRequestModel statusBetweenTwoDateRequestModel, LocalDate starter) {
        return starter.isBefore(statusBetweenTwoDateRequestModel.getEnderDate()) || starter.isEqual(statusBetweenTwoDateRequestModel.getEnderDate());
    }

    private static boolean isValidRange(StatusBetweenTwoDateRequestModel statusBetweenTwoDateRequestModel) {
        return statusBetweenTwoDateRequestModel.getStarterDate().isAfter(statusBetweenTwoDateRequestModel.getEnderDate())
                && !statusBetweenTwoDateRequestModel.getStarterDate().isEqual(statusBetweenTwoDateRequestModel.getEnderDate());
    }

    private StatusModel getStatusForUser(Long userId, LocalDate starter) {
        User user = userRepository.findById(userId).orElseThrow(UserDoesNotExistException::new);
        if (starter.isAfter(LocalDate.now())) {
            return statusMapper.statusToStatusModel(statusRepository.findByUserAndDate(userId, starter)
                    .orElse(new Status(0L, starter, user, DailyStatus.UNKNOWN)));
        }
        return statusMapper.statusToStatusModel(
                statusRepository.findByUserAndDate(userId, starter)
                        .orElse(new Status(0L, starter, user, DailyStatus.ABSENCE)));
    }

    private void teamStatusGuard(Long teamId) {
        User currentLoginUser = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Team team =teamRepository.findById(teamId).orElseThrow(TeamDoesNotExistException::new);
        if (!team.getManager().equals(currentLoginUser))
        {
            throw new StatusAccessIsForbiddenException();
        }

    }

    private void userManagerStatusGuard(Long userId) {
        User currentLoginUser = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User accessUser=userRepository.findById(userId).orElseThrow(UserDoesNotExistException::new);
        if (accessUser.getTeam()==null||accessUser.getTeam().getManager().equals(currentLoginUser))
        {
            throw new StatusAccessIsForbiddenException();
        }
    }
}
