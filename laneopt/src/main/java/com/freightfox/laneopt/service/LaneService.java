package com.freightfox.laneopt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freightfox.laneopt.dto.LaneQuotesResponse;
import com.freightfox.laneopt.dto.LaneQuotesResponse.QuoteResponse;
import com.freightfox.laneopt.entity.Lane;
import com.freightfox.laneopt.entity.TransporterQuote;
import com.freightfox.laneopt.repository.LaneRepository;
import com.freightfox.laneopt.repository.TransporterQuoteRepository;

@Service
public class LaneService {

    private final LaneRepository laneRepository;
    private final TransporterQuoteRepository quoteRepository;

    public LaneService(
            LaneRepository laneRepository,
            TransporterQuoteRepository quoteRepository) {
        this.laneRepository = laneRepository;
        this.quoteRepository = quoteRepository;
    }

    public List<Lane> getAllLanes() {
        return laneRepository.findAll();
    }

    @Transactional(readOnly = true)
    public LaneQuotesResponse getQuotesForLane(Long laneId) {

        Lane lane = laneRepository.findById(laneId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Lane not found: " + laneId));

        List<TransporterQuote> quotes =
                quoteRepository.findByLaneOrderByQuoteAsc(lane);

        List<QuoteResponse> allQuotes = quotes.stream()
                .map(q -> new QuoteResponse(
                        q.getTransporter().getId(),
                        q.getTransporter().getName(),
                        q.getQuote()))
                .toList();

        QuoteResponse cheapest = allQuotes.isEmpty()
                ? null
                : allQuotes.get(0);

        return new LaneQuotesResponse(
                lane.getId(),
                lane.getOrigin(),
                lane.getDestination(),
                cheapest,
                allQuotes);
    }
}