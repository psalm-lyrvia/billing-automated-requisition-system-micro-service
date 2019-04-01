package com.accenture.bars.microservices.server.renzchler.s.oxino.customer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

@Path("customer")
public class CustomerResource {

	@Autowired
	private CustomerService service;

	@GET
	@Path("test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test(){
		return "Test";
	}

	//http://localhost:8090/customer/get-customer/{customerId}
	@GET
	@Path("get-customer/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("customerId") Integer id){
		return service.getCustomer(id);
	}

}
