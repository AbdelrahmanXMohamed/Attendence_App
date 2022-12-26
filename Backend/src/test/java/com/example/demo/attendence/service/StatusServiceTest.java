package com.example.demo.attendence.service;

import com.example.demo.attendence.entity.Status;
import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.exception.StatusInvalidDateException;
import com.example.demo.attendence.exception.UserDoesNotExistException;
import com.example.demo.attendence.mapper.StatusMapper;
import com.example.demo.attendence.model.StatusBetweenTwoDateRequestModel;
import com.example.demo.attendence.model.StatusModel;
import com.example.demo.attendence.model.StatusRequestModel;
import com.example.demo.attendence.repository.StatusRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private final StatusMapper statusMapper = Mappers.getMapper(StatusMapper.class);

    /*
     * Test Cases of Set Status
     */
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
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        List<StatusModel> expected = new ArrayList<>();
        int i = 0;
        for (LocalDate start = starterDate; start.isBefore(enderDate) || start.isEqual(enderDate); start = start.plusDays(1)) {
            if (start.isAfter(LocalDate.now()) && !start.isEqual(LocalDate.now())) {
                expected.add(statusMapper.statusToStatusModel(
                        new Status(0L, start, new User(), DailyStatus.UNKNOWN)));
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
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        List<StatusModel> expected = new ArrayList<>();
        for (LocalDate start = starterDate; start.isBefore(enderDate) || start.isEqual(enderDate); start = start.plusDays(1)) {
            expected.add(new StatusModel(start, new User(), DailyStatus.ABSENCE));
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
        StatusModel expected = new StatusModel(date, new User(), DailyStatus.UNKNOWN);
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        // execute the method being tested
        StatusModel actual = statusService.getReportPerDayForUser(userId, date);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetReportPerDayForUserWithFutureWithVacationDateTestCase() {
        // create mock inputs
        LocalDate date = LocalDate.now().plusDays(10);
        Long userId = 1L;
        StatusModel expected = new StatusModel(date, new User(), DailyStatus.VACATION);
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(statusRepository.findByUserAndDate(userId, date)).thenReturn(Optional.of(new Status(0L, date, new User(), DailyStatus.VACATION)));
        // execute the method being tested
        StatusModel actual = statusService.getReportPerDayForUser(userId, date);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetReportPerDayForUserWithValidDateTestCase() {
        // create mock inputs
        LocalDate date = LocalDate.now().minusDays(10);
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(statusRepository.findByUserAndDate(userId, date)).thenReturn(Optional.of(new Status(0L, date, new User(), DailyStatus.ONSITE)));
        StatusModel expected = new StatusModel(date, new User(), DailyStatus.ONSITE);
        // execute the method being tested
        StatusModel actual = statusService.getReportPerDayForUser(userId, date);
        Assert.assertEquals(expected, actual);
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

//    @Test
//    public void testGetReportForCurrentWeekForUserWithValidDateTestCase() {
//        // create mock inputs
//        LocalDate date = LocalDate.now().plusDays(3);
//        Long userId = 1L;
//        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
//        int i = 0;
//        for (LocalDate start = LocalDate.now(); start.isBefore(date) || start.isEqual(date); start = start.plusDays(1)) {
//            expected.add(statusMapper.statusToStatusModel(
//                    new Status(0L, start, new User(), Math.random() % 2 == 0 ? DailyStatus.ONSITE : DailyStatus.REMOTE)));
//            when(statusRepository.findByUserAndDate(userId, start))
//                    .thenReturn(Optional.of(statusMapper.statusModelToStatus(expected.get(i))));
//            i++;
//        }
//        when(statusRepository.findByUserAndDate(userId, date.minusDays(1)))
//                .thenReturn(Optional.of(new Status(0L, date, new User(), DailyStatus.ONSITE)));
//        StatusModel expected = new StatusModel(date, new User(), DailyStatus.ONSITE);
//        // execute the method being tested
//        List<StatusModel> actual = statusService.getReportForCurrentWeekForUser(userId);
//        Assert.assertEquals(expected, actual);
//    }
}