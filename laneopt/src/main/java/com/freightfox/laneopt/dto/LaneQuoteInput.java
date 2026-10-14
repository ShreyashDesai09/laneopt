package com.freightfox.laneopt.dto;

import jakarta.validation.constraints.*;

public class LaneQuoteInput {

	@NotNull
	private Long laneId;
	
	@NotNull
	@Positive
	private Long quote;
	
	public LaneQuoteInput()
	{	}
	
	public LaneQuoteInput(Long laneId , Long quote)
	{
		this.laneId = laneId;
		this.quote = quote;
	}

	public Long getLaneId() {
		return laneId;
	}

	public void setLaneId(Long laneId) {
		this.laneId = laneId;
	}

	public Long getQuote() {
		return quote;
	}

	public void setQuote(Long quote) {
		this.quote = quote;
	}
	
}
