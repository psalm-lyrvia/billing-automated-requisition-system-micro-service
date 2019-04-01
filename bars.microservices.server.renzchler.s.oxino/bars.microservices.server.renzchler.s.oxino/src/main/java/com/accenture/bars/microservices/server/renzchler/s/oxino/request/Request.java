package com.accenture.bars.microservices.server.renzchler.s.oxino.request;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="request")
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="request_id")
	private int request_id;

	@Column(name="billing_cycle")
	private int billing_cycle;

	@Column(name="start_date", columnDefinition="Date")
	private String start_date;

	@Column(name="end_date", columnDefinition="Date")
	private String end_date;

	public Request(){
		super();
	}

	public Request(int r_id, int b_cycle, String s_date, String e_date){
		super();
		this.request_id = r_id;
		this.billing_cycle = b_cycle;
		this.start_date = s_date;
		this.end_date = e_date;
	}

	public Request(int b_cycle, String s_date, String e_date){
		super();
		this.billing_cycle = b_cycle;
		this.start_date = s_date;
		this.end_date = e_date;
	}

	public int getRequest_id() {
		return request_id;
	}
	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}
	public int getBilling_cycle() {
		return billing_cycle;
	}
	public void setBilling_cycle(int billing_cycle) {
		this.billing_cycle = billing_cycle;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}



}
