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

        List<Lane> lanes = laneRepository.findAll();
        List<TransporterQuote> quotes = quoteRepository.findAll();

        if (lanes.isEmpty()) {
            return new AssignmentResponse(
                    "NO_LANES", 0L,
                    new ArrayList<>(), new ArrayList<>());
        }

        if (quotes.isEmpty()) {
            return new AssignmentResponse(
                    "NO_QUOTES", null,
                    new ArrayList<>(), new ArrayList<>());
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
        int maxTransporters = Math.min(
                request.getMaxTransporters(), transporterList.size());

        for (int count = maxTransporters; count >= 1; count--) {

            BestAssignment best = new BestAssignment();

            findCombinations(
                    lanes, quotesByLane, transporterList,
                    new ArrayList<>(), 0, count, best);

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

    private void findCombinations(
            List<Lane> lanes,
            Map<Long, Map<Long, TransporterQuote>> quotesByLane,
            List<Long> transporterIds,
            List<Long> selected,
            int start,
            int targetCount,
            BestAssignment best) {

        if (selected.size() == targetCount) {
            List<LaneAssignmentResponse> assignments = new ArrayList<>();
            Set<Long> used = new HashSet<>();
            long totalCost = 0L;

            for (Lane lane : lanes) {
                Map<Long, TransporterQuote> laneQuotes =
                        quotesByLane.get(lane.getId());

                if (laneQuotes == null) {
                    return;
                }

                TransporterQuote cheapest = null;

                for (Long id : selected) {
                    TransporterQuote candidate = laneQuotes.get(id);

                    if (candidate != null &&
                            (cheapest == null ||
                             candidate.getQuote() < cheapest.getQuote())) {
                        cheapest = candidate;
                    }
                }

                if (cheapest == null) {
                    return;
                }

                Transporter transporter = cheapest.getTransporter();

                assignments.add(new LaneAssignmentResponse(
                        lane.getId(),
                        lane.getOrigin(),
                        lane.getDestination(),
                        transporter.getId(),
                        transporter.getName(),
                        cheapest.getQuote()));

                used.add(transporter.getId());
                totalCost += cheapest.getQuote();
            }

            if (used.size() != targetCount) {
                findAlternativeAssignments(
                        lanes, quotesByLane, selected, 0,
                        new ArrayList<>(), new HashSet<>(),
                        0L, best, targetCount);
                return;
            }

            if (best.assignments == null || totalCost < best.totalCost) {
                best.totalCost = totalCost;
                best.assignments = assignments;
                best.selectedTransporters = new ArrayList<>(used);
            }

            return;
        }

        for (int i = start; i < transporterIds.size(); i++) {
            selected.add(transporterIds.get(i));

            findCombinations(
                    lanes, quotesByLane, transporterIds,
                    selected, i + 1, targetCount, best);

            selected.remove(selected.size() - 1);
        }
    }

    private void findAlternativeAssignments(
            List<Lane> lanes,
            Map<Long, Map<Long, TransporterQuote>> quotesByLane,
            List<Long> selected,
            int laneIndex,
            List<LaneAssignmentResponse> assignments,
            Set<Long> used,
            long totalCost,
            BestAssignment best,
            int targetCount) {

        if (laneIndex == lanes.size()) {
            if (used.size() == targetCount &&
                    (best.assignments == null ||
                     totalCost < best.totalCost)) {

                best.totalCost = totalCost;
                best.assignments = new ArrayList<>(assignments);
                best.selectedTransporters = new ArrayList<>(used);
            }
            return;
        }

        Lane lane = lanes.get(laneIndex);
        Map<Long, TransporterQuote> laneQuotes =
                quotesByLane.get(lane.getId());

        if (laneQuotes == null) {
            return;
        }

        for (Long transporterId : selected) {
            TransporterQuote quote = laneQuotes.get(transporterId);

            if (quote == null) {
                continue;
            }

            Transporter transporter = quote.getTransporter();

            assignments.add(new LaneAssignmentResponse(
                    lane.getId(),
                    lane.getOrigin(),
                    lane.getDestination(),
                    transporter.getId(),
                    transporter.getName(),
                    quote.getQuote()));

            boolean alreadyUsed = used.contains(transporterId);
            used.add(transporterId);

            findAlternativeAssignments(
                    lanes, quotesByLane, selected,
                    laneIndex + 1, assignments, used,
                    totalCost + quote.getQuote(), best, targetCount);

            assignments.remove(assignments.size() - 1);

            if (!alreadyUsed) {
                used.remove(transporterId);
            }
        }
    }

    private static class BestAssignment {
        private long totalCost;
        private List<LaneAssignmentResponse> assignments;
        private List<Long> selectedTransporters;
    }
}