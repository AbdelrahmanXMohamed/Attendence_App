package com.example.demo.attendence.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data

public class ApproveRequuestModel {

    @Range(min = 0,max = 2, message = "approve must be in range[0,2]")
    int approve;
}
