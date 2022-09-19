package com.bookservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BaseController {
	/**
	 * Handling Field Validations
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	Map<String, String> handleException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<String,String>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldname = ((FieldError) error).getField();
			String message = ((FieldError) error).getDefaultMessage();
			errors.put(fieldname, message);
		});
		return errors;
	}
	/**
	 * Handling Request Body
	 * @param ex
	 * @return
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	ResponseEntity<String> requestParamNotFound(HttpMessageNotReadableException ex) {
		return new ResponseEntity<String>("Error : Request Body is missing ",HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	ResponseEntity<String> sqlException(DataIntegrityViolationException ex) {
		
		return new ResponseEntity<String>("Error : Request Body is missing "+ex.getRootCause().toString(),HttpStatus.NOT_ACCEPTABLE);
	}
	
}