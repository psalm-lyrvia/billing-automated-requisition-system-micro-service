package com.accenture.bars.microservices.server.renzchler.s.oxino.customer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="customer_id")
	private int customerId;
	private String firstName;
	private String lastName;
	private String address;
	private char status;
	private Date dateCreated;
	private String lastEdited;

	public Customer(){
		super();
	}

	public Customer(int cId, String fName, String lName, String addr, char stat, Date dCreated, String lEdited){
		super();
		this.customerId = cId;
		this.firstName = fName;
		this.lastName = lName;
		this.address = addr;
		this.status = stat;
		this.dateCreated = dCreated;
		this.lastEdited = lEdited;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(String lastEdited) {
		this.lastEdited = lastEdited;
	}


}
