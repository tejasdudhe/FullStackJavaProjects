package com.cafe.serviceImp;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cafe.constents.CafeConstants;
import com.cafe.dao.UserDao;
import com.cafe.model.User;
import com.cafe.service.UserService;
import com.cafe.utils.CafeUtils;

import lombok.extern.slf4j.Slf4j;





@Slf4j
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao userdao;

	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
		
		log.info("Inside signup {}",requestMap);
		
		if(validateSignUpMap(requestMap))
		{
			Optional<User> user = userdao.findByEmail(requestMap.get("email"));
			
			if (user.isPresent()) {
				return CafeUtils.getResponseEntity(CafeConstants.EMAIL_ALREADY_EXISTS, HttpStatus.CONFLICT);
			}
			else
			{
				User newUser = createUser(user);
                
                userdao.save(newUser);
			}
		}
		else {
			return CafeUtils.getResponseEntity(CafeConstants.INVALIDE_DATA, HttpStatus.BAD_REQUEST);
		}



		
		return null;
	}

	
	private boolean validateSignUpMap(Map<String, String> requestMap)
	{
		
		if(requestMap.containsKey("userName")&& requestMap.containsKey("contactNumber")&& requestMap.containsKey("email")&& requestMap.containsKey("password"))
		{
			return true;
		}
		return false;
		
	}
	

	private User createUser(Map<String, String> requestMap) {
        User newUser = new User();
        newUser.setEmail(requestMap.get("email"));
        newUser.setPassword(requestMap.get("password"));
        newUser.setUserName(requestMap.get("userName"));  
        newUser.setContactNumber(requestMap.get("contactNumber"));
        newUser.setRole(requestMap.get("role"));
        newUser.setStatus("ACTIVE"); 
        return newUser;
    }

}
