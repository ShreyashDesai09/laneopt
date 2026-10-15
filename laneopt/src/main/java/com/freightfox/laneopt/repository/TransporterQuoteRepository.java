package com.freightfox.laneopt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freightfox.laneopt.entity.Lane;
import com.freightfox.laneopt.entity.TransporterQuote;

public interface TransporterQuoteRepository extends JpaRepository<TransporterQuote, Long>{

	List<TransporterQuote> findByLane(Lane lane);
	
	List<TransporterQuote> findByLaneOrderByQuoteAsc(Lane lane);
	
}
