package com.talent.entity;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class EmployeeData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;
	private String empName;
	private String empEmail;
    @OneToOne(mappedBy = "employeeData",fetch = FetchType.EAGER)
	private EmployeeProfile empSkills;
    @OneToMany(mappedBy = "employeeData",fetch = FetchType.EAGER)
	private  List<Rating> empRating;
}
