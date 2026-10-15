package com.freightfox.laneopt.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freightfox.laneopt.dto.AssignmentRequest;
import com.freightfox.laneopt.dto.AssignmentResponse;
import com.freightfox.laneopt.dto.LaneAssignmentResponse;
import com.freightfox.laneopt.entity.Lane;
import com.freightfox.laneopt.entity.Transporter;
import com.freightfox.laneopt.entity.TransporterQuote;
import com.freightfox.laneopt.repository.LaneRepository;
import com.freightfox.laneopt.repository.TransporterQuoteRepository;

@Service
public class AssignmentService {

    private final LaneRepository laneRepository;
    private final TransporterQuoteRepository quoteRepository;

    public AssignmentService(
            LaneRepository laneRepository,
            TransporterQuoteRepository quoteRepository) {
        this.laneRepository = laneRepository;
        this.quoteRepository = quoteRepository;
    }

    @Transactional(readOnly = true)
    public AssignmentResponse assignTransporters(AssignmentRequest request) {

        int maxTransporters = request.getMaxTransporters();

        List<Lane> lanes = laneRepository.findAll();
        List<TransporterQuote> quotes = quoteRepository.findAll();

        if (lanes.isEmpty()) {
            return new AssignmentResponse(
                    "NO_LANES", 0L, new ArrayList<>(), new ArrayList<>());
        }

        if (quotes.isEmpty()) {
            return new AssignmentResponse(
                    "NO_QUOTES", null, new ArrayList<>(), new ArrayList<>());
        }

        quotes.sort(Comparator.comparingLong(TransporterQuote::getQuote));

        List<LaneAssignmentResponse> assignments = new ArrayList<>();
        Set<Long> assignedLaneIds = new HashSet<>();
        Set<Long> selectedTransporterIds = new HashSet<>();
        long totalCost = 0L;

        for (TransporterQuote quote : quotes) {

            Lane lane = quote.getLane();
            Transporter transporter = quote.getTransporter();

            if (lane == null || transporter == null) {
                continue;
            }

            if (assignedLaneIds.contains(lane.getId())) {
                continue;
            }

            if (!selectedTransporterIds.contains(transporter.getId())
                    && selectedTransporterIds.size() >= maxTransporters) {
                continue;
            }

            LaneAssignmentResponse assignment = new LaneAssignmentResponse(
                    lane.getId(),
                    lane.getOrigin(),
                    lane.getDestination(),
                    transporter.getId(),
                    transporter.getName(),
                    quote.getQuote());

            assignments.add(assignment);
            assignedLaneIds.add(lane.getId());
            selectedTransporterIds.add(transporter.getId());
            totalCost += quote.getQuote();
        }

        if (assignedLaneIds.size() != lanes.size()) {
            return new AssignmentResponse(
                    "NO_FEASIBLE_ASSIGNMENT",
                    null,
                    new ArrayList<>(),
                    new ArrayList<>());
        }

        return new AssignmentResponse(
                "SUCCESS",
                totalCost,
                assignments,
                new ArrayList<>(selectedTransporterIds));
    }
}