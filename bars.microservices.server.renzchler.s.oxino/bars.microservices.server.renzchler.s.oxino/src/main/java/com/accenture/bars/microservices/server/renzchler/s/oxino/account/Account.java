package com.accenture.bars.microservices.server.renzchler.s.oxino.account;

import java.util.Date;

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

import com.accenture.bars.microservices.server.renzchler.s.oxino.customer.Customer;

@Entity
@Table(name="account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="account_id")
	private Integer accountId;

	@Column(name="account_name")
	private String accountName;

	@Column(name="date_created")
	private Date dateCreated;

	@Column(name="is_active")
	private char isActive;

	@Column(name="last_edited")
	private String lastEdited;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Account(){
		super();
	}

	public Account(int aId, String aName, Date dCreated, char isActive, String lEdited){
		super();
		this.accountId = aId;
		this.accountName = aName;
		this.dateCreated = dCreated;
		this.isActive = isActive;
		this.lastEdited = lEdited;
	}

	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public char getIsActive() {
		return isActive;
	}
	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
	public String getLastEdited() {
		return lastEdited;
	}
	public void setLastEdited(String lastEdited) {
		this.lastEdited = lastEdited;
	}

}
