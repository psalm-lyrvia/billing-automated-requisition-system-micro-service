package com.accenture.bars.microservices.client.renzchler.s.oxino.domain;

import java.util.Date;


public class Request {

	private int billingCycle;
	private Date startDate;
	private Date endDate;
	public int getBillingCycle() {
		return billingCycle;
	}
	public void setBillingCycle(int billingCycle) {
		this.billingCycle = billingCycle;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date date) {
		this.startDate = date;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date date) {
		this.endDate = date;
	}


}
