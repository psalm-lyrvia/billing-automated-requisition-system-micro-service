package com.accenture.bars.microservices.server.renzchler.s.oxino.billing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.accenture.bars.microservices.server.renzchler.s.oxino.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "billing")
public class Billing {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "billing_id")
	private int billingId;

	@Column(name = "billing_cycle")
	private int billingCycle;

	@Column(name = "billing_month")
	private String billingMonth;

	@Column(name = "amount")
	private double amount;

	@Column(name = "start_date", columnDefinition = "Date")
	private String startDate;

	@Column(name = "end_date", columnDefinition = "Date")
	private String endDate;

	@Column(name = "last_edited")
	private String lastEdited;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Billing() {
		super();
	}

	public Billing(int b_id, int b_cycle, String b_month, double amount,
			String s_date, String e_date, String edited_by) {
		super();
		this.billingId = b_id;
		this.billingCycle = b_cycle;
		this.billingMonth = b_month;
		this.amount = amount;
		this.startDate = s_date;
		this.endDate = e_date;
		this.lastEdited = edited_by;

	}

	public int getBillingId() {
		return billingId;
	}

	public void setBillingId(int billingId) {
		this.billingId = billingId;
	}

	public int getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(int billingCycle) {
		this.billingCycle = billingCycle;
	}

	public String getBillingMonth() {
		return billingMonth;
	}

	public void setBillingMonth(String billingMonth) {
		this.billingMonth = billingMonth;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(String lastEdited) {
		this.lastEdited = lastEdited;
	}

}
