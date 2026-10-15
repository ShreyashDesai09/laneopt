package com.freightfox.laneopt.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.freightfox.laneopt.dto.LaneQuotesResponse;
import com.freightfox.laneopt.entity.Lane;
import com.freightfox.laneopt.service.LaneService;

@RestController
@RequestMapping("/api/v1/lanes")
public class LaneController {

    private final LaneService laneService;

    public LaneController(LaneService laneService) {
        this.laneService = laneService;
    }

    @GetMapping
    public ResponseEntity<List<Lane>> getAllLanes() {
        return ResponseEntity.ok(laneService.getAllLanes());
    }

    @GetMapping("/{laneId}/quotes")
    public ResponseEntity<LaneQuotesResponse> getQuotesForLane(
            @PathVariable Long laneId) {
        return ResponseEntity.ok(laneService.getQuotesForLane(laneId));
    }
}