package com.ercancelik.questapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ercancelik.questapp.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

	RefreshToken findByUserId(Long userId);
	
}