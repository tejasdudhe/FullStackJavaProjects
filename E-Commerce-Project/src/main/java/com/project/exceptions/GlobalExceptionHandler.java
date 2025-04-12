package com.project.exceptions;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> myMethodsArgumentNotValidException(MethodArgumentNotValidException e) {
		
		Map<String, String> response = new HashMap<>();
		
		e.getBindingResult().getAllErrors().forEach(err->{
			String errField = ((FieldError)err).getField();
			String message = err.getDefaultMessage();
			response.put(errField, message);
		});
		
		return new ResponseEntity<Map<String, String>>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> myResourceNotFoundException(ResourceNotFoundException e) {
		
			String message = e.getMessage();
		return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(APIException.class)
	public ResponseEntity<String> myResourceNotFoundException(APIException e) {
		
			String message = e.getMessage();
		return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
	}
}
