package com.freightfox.laneopt.dto;

public class LaneAssignmentResponse {

	private Long laneId;
	private String origin;
	private String destination;
	
	private Long transporterId;
	private String transporterName;
	
	private Long quote;
	
	public LaneAssignmentResponse() 
	{	}

	public LaneAssignmentResponse(Long laneId, String origin, String destination, Long transporterId,
			String transporterName, Long quote) {
		this.laneId = laneId;
		this.origin = origin;
		this.destination = destination;
		this.transporterId = transporterId;
		this.transporterName = transporterName;
		this.quote = quote;
	}
	
	public Long getLaneId() {
        return laneId;
    }

    public void setLaneId(Long laneId) {
        this.laneId = laneId;
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

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Long getTransporterId() {
        return transporterId;
    }

    public void setTransporterId(Long transporterId) {
        this.transporterId = transporterId;
    }

    public String getTransporterName() {
        return transporterName;
    }

    public void setTransporterName(String transporterName) {
        this.transporterName = transporterName;
    }

    public Long getQuote() {
        return quote;
    }

    public void setQuote(Long quote) {
        this.quote = quote;
    }
    
}
	
