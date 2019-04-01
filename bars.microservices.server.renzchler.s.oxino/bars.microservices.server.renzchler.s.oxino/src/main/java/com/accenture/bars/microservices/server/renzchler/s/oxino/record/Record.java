package com.accenture.bars.microservices.server.renzchler.s.oxino.record;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Record {

	private int billingCycle;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="E MMM dd HH:mm:ss z yyyy")
	private Date startDate;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="E MMM dd HH:mm:ss z yyyy")
	private Date endDate;
	private String accountName;
	private String customerFirstName;
	private String customerLastName;
	private double amount;
	private String sDate;
	private String eDate;

	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	public int getBillingCycle() {
		return billingCycle;
	}
	public void setBillingCycle(int billingCycle) {
		this.billingCycle = billingCycle;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getCustomerFirstName() {
		return customerFirstName;
	}
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}
	public String getCustomerLastName() {
		return customerLastName;
	}
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}


}
