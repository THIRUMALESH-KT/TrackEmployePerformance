package com.talent.UserRequest;

import java.time.LocalDate;

import com.talent.entity.EmployeeData;

import lombok.Data;

@Data
public class EmployeeRatingRequest {
	private Double communication;
    private Double technical;
    private LocalDate startDate;
    private LocalDate endDate;
    private EmployeeData employeeData;
}
