package com.cafe.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.cafe.model.User;

public interface UserDao extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(@Param("email") String email);
	
}
