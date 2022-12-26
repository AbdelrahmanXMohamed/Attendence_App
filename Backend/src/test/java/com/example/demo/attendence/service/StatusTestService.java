package com.example.demo.attendence.service;

import com.example.demo.attendence.entity.Status;
import com.example.demo.attendence.entity.User;
import com.example.demo.attendence.exception.StatusInvalidDateException;
import com.example.demo.attendence.exception.UserDoesNotExistException;
import com.example.demo.attendence.model.StatusRequestModel;
import com.example.demo.attendence.repository.StatusRepository;
import com.example.demo.attendence.repository.UserRepository;
import com.example.demo.attendence.services.impl.StatusServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class StatusTestService {

    @InjectMocks
    private StatusServiceImpl statusService;
    @Mock
    private StatusRepository statusRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    public void testSetStatusSuccess() {
        // create mock inputs
        LocalDate day = LocalDate.now().plusDays(5);
        Long userId = 1L;
        short status = 1;
        Status expectedStatus = new Status(1L, day, new User(), status);
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(statusRepository.save(any(Status.class))).thenReturn(expectedStatus);
        // execute the method being tested
        Status resultStatus =
                statusService.setStatus(new StatusRequestModel(day, userId, status));
        // assert the output is as expected
        Assert.assertTrue(expectedStatus.equals(resultStatus));
    }

    @Test(expected = StatusInvalidDateException.class)
    public void testSetStatusDateErrorTestCase() {
        // create mock inputs
        LocalDate day = LocalDate.of(2022, 12, 10);
        Long userId = 1L;
        short status = 1;
        Status expectedStatus = new Status(1L, day, new User(), status);
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        // execute the method being tested
        statusService.setStatus(new StatusRequestModel(day, userId, status));
    }

    @Test(expected = UserDoesNotExistException.class)
    public void testSetStatusUserDoesNotExistTestCase() {
        // create mock inputs
        LocalDate day = LocalDate.now().minusDays(10);
        Long userId = 1L;
        short status = 1;
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        // execute the method being tested
        statusService.setStatus(new StatusRequestModel(day, userId, status));
    }


}
