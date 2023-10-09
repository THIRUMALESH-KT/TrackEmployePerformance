package com.talent.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talent.UserRequest.EmployeeDataUserRequest;
import com.talent.UserRequest.EmployeeRatingRequest;
import com.talent.entity.EmployeeData;
import com.talent.service.EmployeeDataService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeDataController {

	@Autowired
	private EmployeeDataService employeeDataService;
	
	//Fetch LessThan 2.5 Rating Trainees Details
	@GetMapping("/LessThan2.5Rating")
	public ResponseEntity<Map<String,Object>> getLessThan2_5RatingEmployeeData(){
		log.info("inside getLessThan2_5RatingEmployeeData EmployeeDataController");

		Map<String, Object> map=new HashMap<>();
		map.put("result", "sucess");
		map.put("message",employeeDataService.getLessThanMinReaingEmployeeDataBasedOnDesig() );
		map.put("status", HttpStatus.OK.value());
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	@GetMapping("/AllTrainees")
	public  ResponseEntity<Map<String, Object>> getAllTraineesData(){
		log.info("inside getAllTraineesData EmployeeDataController");

		Map<String, Object> map=new HashMap<>();
		map.put("result", "sucess");
		map.put("message",employeeDataService.getAllTraineesData() );
		map.put("status", HttpStatus.OK.value());
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	@GetMapping("/AllEmployees")
	public ResponseEntity<Map<String,Object>> getAllEmployees(){
		log.info("inside getAllEmployees EmployeeDataController");

		Map<String, Object> map=new HashMap<>();
		map.put("result", "sucess");
		map.put("message",employeeDataService.getAllEmployeesData() );
		map.put("status", HttpStatus.OK.value());
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	@GetMapping("/EmployeeById/{id}")
	public ResponseEntity<Map<String, Object>> getEmployeeById(@PathVariable Long id) throws Exception{
		log.info("inside getEmployeeById EmployeeDataController");

		Map<String, Object> map=new HashMap<>();
		map.put("result", "sucess");
		map.put("message",employeeDataService.getEmployeeOrTraineeById(id) );
		map.put("status", HttpStatus.OK.value());
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	@PostMapping("/InsertEmploye")
	public ResponseEntity<Map<String,Object>> insertEmployee(@RequestBody EmployeeDataUserRequest employeeData) throws Exception{
		log.info("inside insertEmployee EmployeeDataController");
		Map<String, Object> map=new HashMap<>();
		map.put("result", "sucess");
		map.put("message",employeeDataService.inserEmployee(employeeData) );
		map.put("status", HttpStatus.OK.value());
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	@PutMapping("/addRating/{id}")
	public ResponseEntity<Map<String,Object>> addRating(@RequestBody EmployeeRatingRequest request,@PathVariable Long id) throws Exception{
		log.info("inside addRating EmployeeDataController");
		Map<String, Object> map=new HashMap<>();
		map.put("result", "sucess");
		map.put("message",employeeDataService.addRating(request,id) );
		map.put("status", HttpStatus.OK.value());
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	@GetMapping("/BasedOnMonthRating/{month}")
	public ResponseEntity<Map<String, Object>> MonthelyRating(@PathVariable LocalDate month){
		log.info("inside FetchEmployeeDataBaseOnMonthRating EmployeeDataController");
		Map<String, Object> map=new HashMap<>();
		map.put("result", "sucess");
		map.put("message",employeeDataService.MonthlyData(month) );
		map.put("status", HttpStatus.OK.value());
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
}
