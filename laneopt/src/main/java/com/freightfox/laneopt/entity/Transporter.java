package com.freightfox.laneopt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transporters")
public class Transporter {

	@Id
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	public Transporter()
	{	}
	
	public Transporter(Long id, String name) {
        this.id = id;
        this.name = name;
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
	
}
