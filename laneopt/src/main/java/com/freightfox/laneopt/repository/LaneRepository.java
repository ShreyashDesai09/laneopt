package com.freightfox.laneopt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freightfox.laneopt.entity.Lane;

public interface LaneRepository extends JpaRepository<Lane, Long>{
	
}
