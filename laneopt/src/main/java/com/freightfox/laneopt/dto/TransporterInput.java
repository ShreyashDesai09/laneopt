package com.freightfox.laneopt.dto;

import jakarta.validation.constraints.*;

import java.util.List;

import jakarta.validation.*;

public class TransporterInput {

	@NotNull
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotEmpty
	@Valid
	private List<LaneQuoteInput> laneQuotes;

	public TransporterInput()
	{	}
	
	public TransporterInput(@NotNull Long id, @NotBlank String name, List<LaneQuoteInput> laneQuotes) {
		this.id = id;
		this.name = name;
		this.laneQuotes = laneQuotes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LaneQuoteInput> getLaneQuotes() {
		return laneQuotes;
	}

	public void setLaneQuotes(List<LaneQuoteInput> laneQuotes) {
		this.laneQuotes = laneQuotes;
	}
	
}
