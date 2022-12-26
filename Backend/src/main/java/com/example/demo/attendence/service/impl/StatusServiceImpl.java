package com.example.demo.attendence.service.impl;

import com.example.demo.attendence.entity.Status;
import com.example.demo.attendence.entity.Team;
import com.example.demo.attendence.exception.StatusInvalidDateException;
import com.example.demo.attendence.exception.TeamDoesNotExistException;
import com.example.demo.attendence.exception.UserDoesNotExistException;
import com.example.demo.attendence.model.StatusBetweenTwoDateRequestModel;
import com.example.demo.attendence.model.StatusModel;
import com.example.demo.attendence.model.StatusRequestModel;
import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.mapper.StatusMapper;
import com.example.demo.attendence.repository.StatusRepository;
import com.example.demo.attendence.repository.TeamRepository;
import com.example.demo.attendence.repository.UserRepository;
import com.example.demo.attendence.service.StatusService;
import com.example.demo.attendence.utils.DailyStatus;
import org.mapstruct.factory.Mappers;
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
    TeamRepository teamRepository;
    final StatusMapper statusMapper =
            Mappers.getMapper(StatusMapper.class);

    public StatusServiceImpl(StatusRepository statusRepository, UserRepository userRepository, TeamRepository teamRepository) {
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public Status setStatus(StatusRequestModel statusRequest) {
        User user = userRepository.findById(statusRequest.getUserId()).orElseThrow(UserDoesNotExistException::new);
        if (!statusRequest.getDay().isAfter(LocalDate.now())) {
            throw new StatusInvalidDateException("Date must be up coming date");
        }
        StatusModel statusModel = statusMapper.statusRequestModelToStatusModel(statusRequest);
        statusModel.setUser(user);
        return statusRepository.save(statusMapper.statusModelToStatus(statusModel));
    }

    @Override
    public List<StatusModel> getReportForUser(Long id, StatusBetweenTwoDateRequestModel statusBetweenTwoDateRequestModel) {
        statusGuard();
        if (isValidRange(statusBetweenTwoDateRequestModel)) {
            throw new StatusInvalidDateException("Start date can't be after end date");
        }
        List<StatusModel> result = new ArrayList<>();
        for (LocalDate starter = statusBetweenTwoDateRequestModel.getStarterDate(); isValidStarter(statusBetweenTwoDateRequestModel, starter); starter = starter.plusDays(1)) {
            result.add(getStatusForUser(id, starter));
        }
        return result;
    }

    @Override
    public StatusModel getReportPerDayForUser(Long userId, LocalDate date) {
        statusGuard();
        return getStatusForUser(userId, date);
    }

    @Override
    public List<StatusModel> getReportPerDayForTeam(Long teamId, LocalDate date) {
        Team team = teamRepository.findById(teamId).orElseThrow(TeamDoesNotExistException::new);
        return Collections.emptyList();
    }

    @Override
    public List<StatusModel> getReportForCurrentWeekForUser(Long userId) {
        statusGuard();
        List<LocalDate> allDaysOfWeek = Arrays.stream(DayOfWeek.values()).map(LocalDate.now()::with).toList();
        return allDaysOfWeek.stream().map(day -> getStatusForUser(userId, day)).toList();
    }

    @Override
    public List<StatusModel> getReportForCurrentForTeam(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(TeamDoesNotExistException::new);
        return Collections.emptyList();
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
        if (starter.isAfter(LocalDate.now()) && !starter.isEqual(LocalDate.now())) {
            return statusMapper.statusToStatusModel(statusRepository.findByUserAndDate(userId, starter)
                    .orElse(new Status(0L, starter, user, DailyStatus.UNKNOWN)));
        }
        return statusMapper.statusToStatusModel(
                statusRepository.findByUserAndDate(userId, starter)
                        .orElse(new Status(0L, starter, user, DailyStatus.ABSENCE)));
    }

    private void statusGuard() {
        // TODO document why this method is empty
    }
}