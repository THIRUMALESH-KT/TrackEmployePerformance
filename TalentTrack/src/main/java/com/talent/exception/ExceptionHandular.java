package com.talent.exception;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandular {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception ex){
		Map<String, Object> map=new HashMap<>();
		map.put("result : ", "fail");
		map.put("exception from : ", ex.getClass());
		map.put("error message", ex.getLocalizedMessage());
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
	}
}
