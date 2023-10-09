package com.talent.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Rating {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private Double communication;
	    private Double technical;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    @ManyToOne
	    @JoinColumn(name = "employee_id")
	    @JsonBackReference
	    private EmployeeData employeeData;
	    @JsonProperty("employeId")
	    public Long getEmployeeId() {
	        return employeeData != null ? employeeData.getEmployeeId() : null;
	    }
}
