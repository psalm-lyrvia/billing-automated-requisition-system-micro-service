package com.accenture.bars.microservices.server.renzchler.s.oxino.request;

import java.text.ParseException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/")
public class RequestResource {

	@Autowired
	private RequestService service;

	// http://localhost:8090/
	@GET
	@Path("/")
	public String testcb() {
		return "uploadForm";
	}

	// http://localhost:8090/request/test-cb
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("test-cb")
	public String testcsb(String request) {
		return request + " from server";
	}

	// http://localhost:8090/add-request
	@POST
	@Path("add-request")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addRequest(String request) throws JSONException,
			ParseException {
		try {
			service.addRequest(request);
			return Response.status(200).entity("Request saved.").build();
		} catch (Exception e) {
			Logger.getLogger(RequestResource.class).error(e.getMessage());
			return Response.status(404).entity(e.getMessage()).build();
		}

	}

	// http://localhost:8090/request/get-requests
	@GET
	@Path("get-requests")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Request> getAllRequest() {
		return service.getAllRequest();
	}

	// http://localhost:8090/request/delete-request/
	@DELETE
	@Path("delete-request")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteAllRequest() {
		service.deleteAllRequest();
	}

}
