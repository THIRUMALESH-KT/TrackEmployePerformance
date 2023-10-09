package com.talent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talent.entity.EmployeeProfile;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {

}
