package com.talent.UserRequest;

import com.talent.entity.EmployeeProfile;
import com.talent.entity.Rating;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class EmployeeDataUserRequest {

	private String empName;
	private String empEmail;
    @OneToOne(mappedBy = "employeeData",fetch = FetchType.EAGER)
	private EmployeeProfile empSkills;
    @OneToOne(mappedBy = "employeeData",fetch = FetchType.EAGER)
	private  Rating empRating;
}
