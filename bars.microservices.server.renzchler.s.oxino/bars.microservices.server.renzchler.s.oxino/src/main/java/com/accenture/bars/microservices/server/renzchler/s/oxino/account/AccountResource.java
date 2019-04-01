package com.accenture.bars.microservices.server.renzchler.s.oxino.account;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

@Path("account")
public class AccountResource {

	@Autowired
	private AccountService service;

	//http://localhost:8090/account/get-account/{accountId}
	@GET
	@Path("get-account/{accountId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Account getAccount(@PathParam("accountId") Integer id){
		return service.getAccount(id);
	}

}
