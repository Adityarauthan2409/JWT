package com.example.JWT.repository;

import java.util.Optional;

import com.example.JWT.user.UserD;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserD, Integer> {
    
    Optional<UserD> findByEmail(String email);
	
}

