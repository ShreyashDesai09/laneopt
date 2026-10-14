package com.freightfox.laneopt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lanes")
public class Lane {

	@Id 
	private Long id;
	
	@Column(nullable = false)
	private String origin;
	
	@Column(nullable = false)
	private String destination;
	
	public Lane()
	{	}
	
	public Lane(Long id, String origin, String destination)
	{	
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

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
	
}
