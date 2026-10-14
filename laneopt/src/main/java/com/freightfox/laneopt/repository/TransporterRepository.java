package com.freightfox.laneopt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freightfox.laneopt.entity.Transporter;

public interface TransporterRepository extends JpaRepository<Transporter, Long>{

}
