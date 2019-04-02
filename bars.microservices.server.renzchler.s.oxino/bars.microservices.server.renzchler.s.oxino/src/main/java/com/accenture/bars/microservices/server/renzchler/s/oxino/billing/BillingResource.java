package com.accenture.bars.microservices.server.renzchler.s.oxino.billing;

import java.text.ParseException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;


@Path("billing")
public class BillingResource {

	@Autowired
	private BillingService service;

	// http://localhost:8090/billing/billing-list
	@GET
	@Path("billing-list")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Billing> getAllBilling() {
		return service.getAllBilling();
	}

	// http://localhost:8090/billing/get-billing
	@GET
	@Path("get-billing")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Billing> getBilling(String request) throws JSONException,
			ParseException {
		return service.getCustomerBilling(request);
	}



//	// http://localhost:8090/billing/get-account
//	@GET
//	@Path("get-account")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Collection<Billing> getBillingAccount(String request){
//		return service.getBillingAccount(request);
//	}
}
