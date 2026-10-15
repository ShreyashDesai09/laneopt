package com.freightfox.laneopt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import com.freightfox.laneopt.dto.*;

import com.freightfox.laneopt.entity.*;

import com.freightfox.laneopt.repository.*;

import jakarta.persistence.EntityManager;

@Service
public class TransporterService {

	private final LaneRepository laneRepository;
	private final TransporterRepository transporterRepository;
	private final TransporterQuoteRepository transporterQuoteRepository;
	private final EntityManager entityManager;
	
	public TransporterService(LaneRepository laneRepository, TransporterRepository transporterRepository,
			TransporterQuoteRepository transporterQuoteRepository, EntityManager entityManager) {
		this.laneRepository = laneRepository;
		this.transporterRepository = transporterRepository;
		this.transporterQuoteRepository = transporterQuoteRepository;
		this.entityManager = entityManager;
	}
	@Transactional
	public String saveInput(TransporterInputRequest request)
	{
		transporterQuoteRepository.deleteAllInBatch();
		transporterRepository.deleteAllInBatch();
		laneRepository.deleteAllInBatch();
		
		List<Lane> lanes = new ArrayList<>();
		
		for(LaneInput laneInput : request.getLanes())
		{
			Lane lane = new Lane(
					laneInput.getId(),
					laneInput.getOrigin(),
					laneInput.getDestination());
			
			lanes.add(lane);
			
		}
		
		laneRepository.saveAll(lanes);
		
		List<Transporter> transporters = new ArrayList<>();
		
		for (TransporterInput transporterInput : request.getTransporters()) {
            Transporter transporter = new Transporter(
                    transporterInput.getId(),
                    transporterInput.getName());

            transporters.add(transporter);
        }

        transporterRepository.saveAll(transporters);

        entityManager.flush();
        entityManager.clear();

        List<TransporterQuote> quotes = new ArrayList<>();

        for (TransporterInput transporterInput : request.getTransporters()) {

            Transporter transporter = transporterRepository
                    .findById(transporterInput.getId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Transporter not found: "
                                            + transporterInput.getId()));

            for (LaneQuoteInput quoteInput : transporterInput.getLaneQuotes()) {

                Lane lane = laneRepository
                        .findById(quoteInput.getLaneId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Lane not found: "
                                                + quoteInput.getLaneId()));

                TransporterQuote quote = new TransporterQuote(
                        transporter,
                        lane,
                        quoteInput.getQuote());

                quotes.add(quote);
            }
        }

        transporterQuoteRepository.saveAll(quotes);

        return "Transporter data saved successfully";
        
		
	}

	public List<Transporter> getAllTransporters() {
		return transporterRepository.findAll();
	}
	
	
	
}
