package com.cafe.RestImp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.Rest.UserRest;
import com.cafe.constents.CafeConstants;
import com.cafe.service.UserService;
import com.cafe.utils.CafeUtils;

@RestController
public class UserRestImpl implements UserRest{
	
	@Autowired
	UserService userservice;

	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		
		try {
			
			return userservice.signUp(requestMap);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
