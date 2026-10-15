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

	public Integer getMaxTranInteger() {
		return maxTransporters;
	}

	public void setMaxTranInteger(Integer maxTransporters) {
		this.maxTransporters = maxTransporters;
	}
	
}
