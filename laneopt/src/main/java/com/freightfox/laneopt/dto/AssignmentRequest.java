package com.freightfox.laneopt.dto;

import jakarta.validation.constraints.*;

public class AssignmentRequest {

	@NotNull
	@Positive
	private Integer maxTransporters;
	
	public AssignmentRequest()
	{	}

	public AssignmentRequest(Integer maxTransporters) {
		this.maxTransporters = maxTransporters;
	}

	public Integer getMaxTransporters() {
		return maxTransporters;
	}

	public void setMaxTransporters(Integer maxTransporters) {
		this.maxTransporters = maxTransporters;
	}
	
}
