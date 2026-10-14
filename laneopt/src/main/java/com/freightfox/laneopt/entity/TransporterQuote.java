package com.freightfox.laneopt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transporter_quotes",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "uk_transporter_lane",
			columnNames = {"transporter_id", "lane_id"}
			)
	}
)
public class TransporterQuote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "transporter_id", nullable = false)
	private Transporter transporter;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "lane_id", nullable = false)
	private Lane lane;
	
	@Column(nullable = false)
	private Long quote;
	
	public TransporterQuote()
	{	}
	
	public TransporterQuote(Transporter transporter, Lane lane, Long quote)
	{	
		this.transporter = transporter;
		this.lane = lane;
		this.quote = quote;
	}

	public Long getId() {
		return id;
	}

	public Transporter getTransporter() {
		return transporter;
	}

	public void setTransporter(Transporter transporter) {
		this.transporter = transporter;
	}

	public Lane getLane() {
		return lane;
	}

	public void setLane(Lane lane) {
		this.lane = lane;
	}

	public Long getQuote() {
		return quote;
	}

	public void setQuote(Long quote) {
		this.quote = quote;
	}
	
	
	
}
