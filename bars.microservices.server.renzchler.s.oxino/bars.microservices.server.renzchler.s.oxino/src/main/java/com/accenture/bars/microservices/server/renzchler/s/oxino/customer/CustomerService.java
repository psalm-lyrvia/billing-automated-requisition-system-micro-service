package com.accenture.bars.microservices.server.renzchler.s.oxino.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@Autowired
	private ICustomerRepository service;

	public Customer getCustomer(int id){
		return service.findOne(id);
	}

}
