package com.cafe.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtils {

	public CafeUtils() {
		super();
		
	}
	
	public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
	    return new ResponseEntity<>("{\"message\":\"" + responseMessage + "\"}", httpStatus);
	}


}
