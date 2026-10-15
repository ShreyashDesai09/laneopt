package com.freightfox.laneopt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
                    "NO_LANES",
                    0L,
                    new ArrayList<>(),
                    new ArrayList<>());
        }

        if (quotes.isEmpty()) {
            return new AssignmentResponse(
                    "NO_QUOTES",
                    null,
                    new ArrayList<>(),
                    new ArrayList<>());
        }

        Map<Long, Map<Long, TransporterQuote>> quotesByLane =
                new HashMap<>();

        Set<Long> transporterIds = new HashSet<>();

        for (TransporterQuote quote : quotes) {

            Long laneId = quote.getLane().getId();
            Long transporterId = quote.getTransporter().getId();

            quotesByLane
                    .computeIfAbsent(laneId, key -> new HashMap<>())
                    .put(transporterId, quote);

            transporterIds.add(transporterId);
        }

        List<Long> transporterList = new ArrayList<>(transporterIds);

        int targetCount = Math.min(
                maxTransporters,
                transporterList.size());

        for (int count = targetCount; count >= 1; count--) {

            BestAssignment best = new BestAssignment();

            findBestCombination(
                    lanes,
                    quotesByLane,
                    transporterList,
                    new ArrayList<>(),
                    0,
                    count,
                    best);

            if (best.assignments != null) {

                return new AssignmentResponse(
                        "SUCCESS",
                        best.totalCost,
                        best.assignments,
                        best.selectedTransporters);
            }
        }

        return new AssignmentResponse(
                "NO_FEASIBLE_ASSIGNMENT",
                null,
                new ArrayList<>(),
                new ArrayList<>());
    }

    private void findBestCombination(
            List<Lane> lanes,
            Map<Long, Map<Long, TransporterQuote>> quotesByLane,
            List<Long> transporterIds,
            List<Long> selected,
            int start,
            int targetCount,
            BestAssignment best) {

        if (selected.size() == targetCount) {

            List<LaneAssignmentResponse> assignments =
                    new ArrayList<>();

            long totalCost = 0L;

            // Find the cheapest quote for every lane
            // among the currently selected transporters.
            for (Lane lane : lanes) {

                Map<Long, TransporterQuote> laneQuotes =
                        quotesByLane.get(lane.getId());

                if (laneQuotes == null) {
                    return;
                }

                TransporterQuote cheapestQuote = null;

                for (Long transporterId : selected) {

                    TransporterQuote candidate =
                            laneQuotes.get(transporterId);

                    if (candidate != null
                            && (cheapestQuote == null
                            || candidate.getQuote()
                                    < cheapestQuote.getQuote())) {

                        cheapestQuote = candidate;
                    }
                }

                // This combination cannot cover every lane.
                if (cheapestQuote == null) {
                    return;
                }

                Transporter transporter =
                        cheapestQuote.getTransporter();

                assignments.add(new LaneAssignmentResponse(
                        lane.getId(),
                        lane.getOrigin(),
                        lane.getDestination(),
                        transporter.getId(),
                        transporter.getName(),
                        cheapestQuote.getQuote()));

                totalCost += cheapestQuote.getQuote();
            }

            // Determine which transporters were actually assigned.
            Set<Long> actuallyUsed = new HashSet<>();

            for (LaneAssignmentResponse assignment : assignments) {
                actuallyUsed.add(assignment.getTransporterId());
            }

            // Reject combinations where a selected transporter
            // was not assigned any lane.
            System.out.println("Selected combination: " + selected);
            System.out.println("Actually used: " + actuallyUsed);
            System.out.println("Total cost: " + totalCost);
            System.out.println("Target count: " + targetCount);

            if (actuallyUsed.size() != targetCount) {
                return;
            }

            // Keep the cheapest valid assignment for this transporter count.
            if (best.assignments == null
                    || totalCost < best.totalCost) {

                best.totalCost = totalCost;
                best.assignments = assignments;
                best.selectedTransporters =
                        new ArrayList<>(actuallyUsed);
            }

            return;
        }

        // Generate every possible transporter combination.
        for (int i = start; i < transporterIds.size(); i++) {

            selected.add(transporterIds.get(i));

            findBestCombination(
                    lanes,
                    quotesByLane,
                    transporterIds,
                    selected,
                    i + 1,
                    targetCount,
                    best);

            selected.remove(selected.size() - 1);
        }
    }

    private static class BestAssignment {

        private long totalCost;

        private List<LaneAssignmentResponse> assignments;

        private List<Long> selectedTransporters;
    }
}