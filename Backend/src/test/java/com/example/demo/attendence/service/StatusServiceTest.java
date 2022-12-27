package com.example.demo.attendence.service;

import com.example.demo.attendence.entity.Status;
import com.example.demo.attendence.entity.Team;
import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.exception.StatusInvalidDateException;
import com.example.demo.attendence.exception.TeamDoesNotExistException;
import com.example.demo.attendence.exception.UserDoesNotExistException;
import com.example.demo.attendence.mapper.StatusMapper;
import com.example.demo.attendence.model.StatusBetweenTwoDateRequestModel;
import com.example.demo.attendence.model.StatusModel;
import com.example.demo.attendence.model.StatusRequestModel;
import com.example.demo.attendence.repository.StatusRepository;
import com.example.demo.attendence.repository.TeamRepository;
import com.example.demo.attendence.repository.UserRepository;
import com.example.demo.attendence.service.impl.StatusServiceImpl;
import com.example.demo.attendence.utils.DailyStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class StatusServiceTest {
    @InjectMocks
    private StatusServiceImpl statusService;
    @Mock
    private StatusRepository statusRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TeamRepository teamRepository;

    private final StatusMapper statusMapper = Mappers.getMapper(StatusMapper.class);

    /*
     * Test Cases of Set Status
     */
    @Test(expected = StatusInvalidDateException.class)
    public void testSetStatusDateErrorTestCase() {
        // create mock inputs
        LocalDate day = LocalDate.now().minusDays(10);
        Long userId = 1L;
        DailyStatus status = DailyStatus.ONSITE;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        // execute the method being tested
        statusService.setStatus(new StatusRequestModel(day, userId, status));
    }

    @Test(expected = UserDoesNotExistException.class)
    public void testSetStatusUserDoesNotExistTestCase() {
        // create mock inputs
        LocalDate day = LocalDate.now().minusDays(10);
        Long userId = 1L;
        DailyStatus status = DailyStatus.ONSITE;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // execute the method being tested
        statusService.setStatus(new StatusRequestModel(day, userId, status));
    }

    @Test
    public void testSetStatusSuccess() {
        // create mock inputs
        LocalDate day = LocalDate.now().plusDays(5);
        Long userId = 1L;
        DailyStatus status = DailyStatus.ONSITE;
        Status expectedStatus = new Status(userId, day, new User(), status);
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(statusRepository.save(any(Status.class))).thenReturn(expectedStatus);
        // execute the method being tested
        Status resultStatus =
                statusService.setStatus(new StatusRequestModel(day, userId, status));
        // assert the output is as expected
        Assert.assertEquals(expectedStatus, resultStatus);
    }

    /*
     * Test Cases of Get Report For User
     */
    @Test(expected = UserDoesNotExistException.class)
    public void testGetReportForUserUserDoesNotExistTestCase() {
        // create mock inputs
        LocalDate starterDate = LocalDate.now().minusDays(10);
        LocalDate enderDate = LocalDate.now().minusDays(5);
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // execute the method being tested
        statusService.getReportForUser(userId, new StatusBetweenTwoDateRequestModel(starterDate, enderDate));
    }

    @Test(expected = StatusInvalidDateException.class)
    public void testGetReportForUserInvalidStartDateTestCase() {
        // create mock inputs
        LocalDate starterDate = LocalDate.now().minusDays(5);
        LocalDate enderDate = LocalDate.now().minusDays(10);
        Long userId = 1L;
        // execute the method being tested
        statusService.getReportForUser(userId, new StatusBetweenTwoDateRequestModel(starterDate, enderDate));
    }

    @Test
    public void testGetReportForUserWithFutureDateTestCase() {
        // create mock inputs
        LocalDate starterDate = LocalDate.now().minusDays(5);
        LocalDate enderDate = LocalDate.now().plusDays(5);
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        List<StatusModel> expected = new ArrayList<>();
        int i = 0;
        for (LocalDate start = starterDate; start.isBefore(enderDate) || start.isEqual(enderDate); start = start.plusDays(1)) {
            if (start.isAfter(LocalDate.now()) && !start.isEqual(LocalDate.now())) {
                expected.add(statusMapper.statusToStatusModel(
                        new Status(0L, start, user, DailyStatus.UNKNOWN)));
                continue;
            }
            expected.add(statusMapper.statusToStatusModel(
                    new Status(0L, start, user, Math.random() % 2 == 0 ? DailyStatus.ONSITE : DailyStatus.REMOTE)));
            when(statusRepository.findByUserAndDate(userId, start))
                    .thenReturn(Optional.of(statusMapper.statusModelToStatus(expected.get(i))));
            i++;
        }
        // execute the method being tested
        List<StatusModel> actual = statusService.getReportForUser(userId, new StatusBetweenTwoDateRequestModel(starterDate, enderDate));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetReportForUserWithValidAllDateExistTestCase() {
        // create mock inputs
        LocalDate starterDate = LocalDate.now().minusDays(10);
        LocalDate enderDate = LocalDate.now().minusDays(5);
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        List<StatusModel> expected = new ArrayList<>();
        int i = 0;
        for (LocalDate start = starterDate; start.isBefore(enderDate) || start.isEqual(enderDate); start = start.plusDays(1)) {
            expected.add(statusMapper.statusToStatusModel(
                    new Status(0L, start, new User(), Math.random() % 2 == 0 ? DailyStatus.ONSITE : DailyStatus.REMOTE)));
            when(statusRepository.findByUserAndDate(userId, start))
                    .thenReturn(Optional.of(statusMapper.statusModelToStatus(expected.get(i))));
            i++;
        }
        // execute the method being tested
        List<StatusModel> actual = statusService.getReportForUser(userId, new StatusBetweenTwoDateRequestModel(starterDate, enderDate));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetReportForUserWithValidSomeMissingDateExistTestCase() {
        // create mock inputs
        LocalDate starterDate = LocalDate.now().minusDays(100);
        LocalDate enderDate = LocalDate.now().minusDays(1);
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        List<StatusModel> expected = new ArrayList<>();
        int i = 0;
        for (LocalDate start = starterDate; start.isBefore(enderDate) || start.isEqual(enderDate); start = start.plusDays(1)) {
            if (Math.random() % 2 == 0) {
                expected.add(new StatusModel(start, new User(), DailyStatus.ABSENCE));
                continue;
            }
            expected.add(statusMapper.statusToStatusModel(
                    new Status(0L, start, new User(), Math.random() % 2 == 0 ? DailyStatus.ONSITE : DailyStatus.REMOTE)));
            when(statusRepository.findByUserAndDate(userId, start))
                    .thenReturn(Optional.of(statusMapper.statusModelToStatus(expected.get(i))));
            i++;
        }
        // execute the method being tested
        List<StatusModel> actual = statusService.getReportForUser(userId, new StatusBetweenTwoDateRequestModel(starterDate, enderDate));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetReportForUserWithValidAllMissingDateExistTestCase() {
        // create mock inputs
        LocalDate starterDate = LocalDate.now().minusDays(10);
        LocalDate enderDate = LocalDate.now().minusDays(5);
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        List<StatusModel> expected = new ArrayList<>();
        for (LocalDate start = starterDate; start.isBefore(enderDate) || start.isEqual(enderDate); start = start.plusDays(1)) {
            expected.add(new StatusModel(start, user, DailyStatus.ABSENCE));
        }
        // execute the method being tested
        List<StatusModel> actual = statusService.getReportForUser(userId, new StatusBetweenTwoDateRequestModel(starterDate, enderDate));
        Assert.assertEquals(expected, actual);
    }

    /*
     * Test Cases of Get Report Per Day For User
     */
    @Test(expected = UserDoesNotExistException.class)
    public void testGetReportPerDayForUserUserDoesNotExistTestCase() {
        // create mock inputs
        LocalDate date = LocalDate.now().minusDays(10);
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // execute the method being tested
        statusService.getReportPerDayForUser(userId, date);
    }

    @Test
    public void testGetReportPerDayForUserWithFutureDateTestCase() {
        // create mock inputs
        LocalDate date = LocalDate.now().plusDays(10);
        Long userId = 1L;
        User user = new User();
        StatusModel expected = new StatusModel(date, user, DailyStatus.UNKNOWN);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        // execute the method being tested
        StatusModel actual = statusService.getReportPerDayForUser(userId, date);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetReportPerDayForUserWithFutureWithVacationDateTestCase() {
        // create mock inputs
        LocalDate date = LocalDate.now().plusDays(10);
        Long userId = 1L;
        User user = new User();
        StatusModel expected = new StatusModel(date, user, DailyStatus.VACATION);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(statusRepository.findByUserAndDate(userId, date)).thenReturn(Optional.of(new Status(0L, date, user, DailyStatus.VACATION)));
        // execute the method being tested
        StatusModel actual = statusService.getReportPerDayForUser(userId, date);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetReportPerDayForUserWithValidDateTestCase() {
        // create mock inputs
        LocalDate date = LocalDate.now().minusDays(10);
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(statusRepository.findByUserAndDate(userId, date)).thenReturn(Optional.of(new Status(0L, date, user, DailyStatus.ONSITE)));
        StatusModel expected = new StatusModel(date, user, DailyStatus.ONSITE);
        // execute the method being tested
        StatusModel actual = statusService.getReportPerDayForUser(userId, date);
        Assert.assertEquals(expected, actual);
    }

    /*
     * Test Cases of Get Report Per Day For Team
     */
    @Test(expected = TeamDoesNotExistException.class)
    public void testGetReportPerDayForTeamTeamDoesNotExistTestCase() {
        // create mock inputs
        LocalDate date = LocalDate.now().minusDays(10);
        Long teamId = 1L;
        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());
        // execute the method being tested
        statusService.getReportPerDayForTeam(teamId, date);
    }

    @Test
    public void testGetReportPerDayForTeamWithValidDateTestCase() {
        // create mock inputs
        LocalDate date = LocalDate.now();
        Long teamId = 1L;
        Long managerUserId = 2L;
        Long memberUserId1 = 3L;
        Long memberUserId2 = 4L;

        User managerUser = new User();
        User memberUser1 = new User();
        User memberUser2 = new User();

        Team team = new Team();
        team.setManager(managerUser);
        team.setUsers(List.of(memberUser1, memberUser2));

        List<LocalDate> allDaysOfWeek = Arrays.stream(DayOfWeek.values()).map(LocalDate.now()::with).toList();
        when(userRepository.findById(managerUserId)).thenReturn(Optional.of(managerUser));
        when(userRepository.findById(memberUserId1)).thenReturn(Optional.of(memberUser1));
        when(userRepository.findById(memberUserId2)).thenReturn(Optional.of(memberUser2));
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        List<StatusModel> expected = new ArrayList<>();

        for (LocalDate start = date; start.isAfter(date.minusDays(2)) || start.isEqual(date.minusDays(2)); start = start.minusDays(1)) {
            DailyStatus status1 = Math.random() % 2 == 0 ? DailyStatus.REMOTE : DailyStatus.ONSITE;
            DailyStatus status2 = Math.random() % 2 == 0 ? DailyStatus.REMOTE : DailyStatus.ONSITE;

            expected.add(new StatusModel(start, memberUser1, status1));
            expected.add(new StatusModel(start, memberUser2, status2));

            when(statusRepository.findByUserAndDate(memberUserId1, start)).thenReturn(Optional.of(new Status(0L, start, memberUser1, status1)));
            when(statusRepository.findByUserAndDate(memberUserId2, start)).thenReturn(Optional.of(new Status(0L, start, memberUser2, status2)));
        }

        for (LocalDate start = date.plusDays(1); start.isBefore(date.plusDays(3)); start = start.plusDays(1)) {
            DailyStatus status = DailyStatus.VACATION;
            expected.add(new StatusModel(start, memberUser1, status));
            expected.add(new StatusModel(start, memberUser2, status));
            when(statusRepository.findByUserAndDate(memberUserId1, start)).thenReturn(Optional.of(new Status(0L, start, memberUser1, status)));
        }

        expected = expected.stream().filter(status -> allDaysOfWeek.contains(status.getDay())).collect(Collectors.toList());

        for (LocalDate day : allDaysOfWeek) {
            if (expected.stream().noneMatch(expectedItem -> expectedItem.getDay().isEqual(day))) {
                if (day.isAfter(LocalDate.now())) {
                    expected.add(new StatusModel(day, memberUser1, DailyStatus.UNKNOWN));
                    expected.add(new StatusModel(day, memberUser2, DailyStatus.UNKNOWN));
                    continue;
                }
                expected.add(new StatusModel(day, memberUser1, DailyStatus.ABSENCE));
                expected.add(new StatusModel(day, memberUser2, DailyStatus.ABSENCE));
            }
        }

        // execute the method being tested
        List<StatusModel> actual = statusService.getReportForCurrentWeekForUser(teamId);
        Assert.assertTrue(expected.containsAll(actual));
    }

    /*
     * Test Cases of Get Report For Current Week For User
     */
    @Test(expected = UserDoesNotExistException.class)
    public void testGetReportForCurrentWeekForUserUserDoesNotExistTestCase() {
        // create mock inputs
        LocalDate date = LocalDate.now().minusDays(10);
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // execute the method being tested
        statusService.getReportForCurrentWeekForUser(userId);
    }

    @Test
    public void testGetReportForCurrentWeekForUserWithValidDateTestCase() {
        // create mock inputs
        LocalDate date = LocalDate.now();
        Long userId = 1L;
        User user = new User();
        List<LocalDate> allDaysOfWeek = Arrays.stream(DayOfWeek.values()).map(LocalDate.now()::with).toList();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        List<StatusModel> expected = new ArrayList<>();

        for (LocalDate start = date; start.isAfter(date.minusDays(2)) || start.isEqual(date.minusDays(2)); start = start.minusDays(1)) {
            DailyStatus status = Math.random() % 2 == 0 ? DailyStatus.REMOTE : DailyStatus.ONSITE;
            expected.add(new StatusModel(start, user, status));
            when(statusRepository.findByUserAndDate(userId, start)).thenReturn(Optional.of(new Status(0L, start, user, status)));
        }

        for (LocalDate start = date.plusDays(1); start.isBefore(date.plusDays(3)); start = start.plusDays(1)) {
            DailyStatus status = DailyStatus.VACATION;
            expected.add(new StatusModel(start, user, status));
            when(statusRepository.findByUserAndDate(userId, start)).thenReturn(Optional.of(new Status(0L, start, user, status)));
        }

        expected = expected.stream().filter(status -> allDaysOfWeek.contains(status.getDay())).collect(Collectors.toList());

        for (LocalDate day : allDaysOfWeek) {
            if (expected.stream().noneMatch(expectedItem -> expectedItem.getDay().isEqual(day))) {
                if (day.isAfter(LocalDate.now())) {
                    expected.add(new StatusModel(day, user, DailyStatus.UNKNOWN));
                    continue;
                }
                expected.add(new StatusModel(day, user, DailyStatus.ABSENCE));
            }
        }

        // execute the method being tested
        List<StatusModel> actual = statusService.getReportForCurrentWeekForUser(userId);
        Assert.assertTrue(expected.containsAll(actual));
    }
    /*
     * Test Cases of Get Report For Current Week For User
     */
}