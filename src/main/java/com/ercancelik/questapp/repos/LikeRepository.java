package com.ercancelik.questapp.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ercancelik.questapp.entities.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

}
