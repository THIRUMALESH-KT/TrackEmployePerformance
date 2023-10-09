package com.talent.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talent.entity.EmployeeData;

public interface EmployeeDataRepository extends JpaRepository<EmployeeData	, Long>{

    List<EmployeeData> findByEmpSkillsDesignationAndEmpRatingTechnicalLessThanAndEmpRatingStartDate(String designation, Double d, LocalDate startDate);

	List<EmployeeData> findByEmpSkillsDesignation(String string);


}
