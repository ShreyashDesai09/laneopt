package com.freightfox.laneopt.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.freightfox.laneopt.dto.*;
import com.freightfox.laneopt.entity.Transporter;
import com.freightfox.laneopt.service.*;

@RestController
@RequestMapping("/api/v1/transporters")
public class TransporterController {

	private final TransporterService transporterService;
	
	public TransporterController(TransporterService transporterService)
	{
		this.transporterService = transporterService;
	}
	
	@PostMapping("/input")
	public ResponseEntity<ApiResponse<String>> saveInput(@Valid @RequestBody TransporterInputRequest request)
	{
		String message = transporterService.saveInput(request);
	
		ApiResponse<String> response = new ApiResponse<>(true, message, null);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<Transporter>> getAllTransporters() {
	    return ResponseEntity.ok(transporterService.getAllTransporters());
	}
	
}
