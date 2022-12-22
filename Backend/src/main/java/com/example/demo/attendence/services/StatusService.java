package com.example.demo.attendence.services;

import com.example.demo.attendence.entity.Status;
import com.example.demo.attendence.model.StatusRequestModel;

public interface StatusService {


    Status setStatus(StatusRequestModel statusRequest);
}


