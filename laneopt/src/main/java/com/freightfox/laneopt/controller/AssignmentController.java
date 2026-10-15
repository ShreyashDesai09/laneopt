package com.freightfox.laneopt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freightfox.laneopt.dto.ApiResponse;
import com.freightfox.laneopt.dto.AssignmentResponse;
import com.freightfox.laneopt.service.AssignmentService;
import com.freightfox.laneopt.dto.AssignmentRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/transporters")
public class AssignmentController {

	private final AssignmentService assignmentService;

	public AssignmentController(AssignmentService assignmentService) {
		this.assignmentService = assignmentService;
	}
	
	@PostMapping("/assignment")
	public ResponseEntity<ApiResponse<AssignmentResponse>> assignmentTransporters(
	        @Valid @RequestBody AssignmentRequest request) {

	    ApiResponse<AssignmentResponse> response = new ApiResponse<>(
	            true,
	            "Assignment completed",
	            assignmentService.assignTransporters(request)
	    );

	    return ResponseEntity.ok(response);
	}
	
}
