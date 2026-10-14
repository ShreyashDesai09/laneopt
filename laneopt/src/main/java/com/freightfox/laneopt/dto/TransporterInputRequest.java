package com.freightfox.laneopt.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class TransporterInputRequest {

	@NotEmpty
	@Valid
	private List<LaneInput> lanes;
	
	@NotEmpty
	@Valid
	private List<TransporterInput> transporters;
	
	public TransporterInputRequest()
	{	}

	public TransporterInputRequest(@NotEmpty @Valid List<LaneInput> lanes,
			@NotEmpty @Valid List<TransporterInput> transporters) {
		super();
		this.lanes = lanes;
		this.transporters = transporters;
	}

	public List<LaneInput> getLanes() {
		return lanes;
	}

	public void setLanes(List<LaneInput> lanes) {
		this.lanes = lanes;
	}

	public List<TransporterInput> getTransporters() {
		return transporters;
	}

	public void setTransporters(List<TransporterInput> transporters) {
		this.transporters = transporters;
	}
	
}
