package com.freightfox.laneopt.dto;

import jakarta.validation.constraints.*;

public class LaneInput {

	@NotNull
	private Long id;
	
	@NotBlank
	private String origin;

	@NotBlank
	private String destination;
	
	public LaneInput()
	{	}

	public LaneInput(Long id, String origin, String destination) {
		this.id = id;
		this.origin = origin;
		this.destination = destination;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDescription(String destination) {
		this.destination = destination;
	}
	
}
