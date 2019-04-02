package com.accenture.bars.microservices.server.renzchler.s.oxino.record;

import java.text.ParseException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.bars.microservices.server.renzchler.s.oxino.request.RequestResource;

@Path("/")
public class RecordResource {

	@Autowired
	private RecordService service;

	// http://localhost:8090/
	@GET
	@Path("/")
	public Response test() {
		return Response.status(200).build();
	}

	// http://localhost:8090/get-records
	@GET
	@Path("get-records")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecords() throws ParseException, JSONException {

		try {
			JSONObject obj = new JSONObject();

			JSONArray arr = new JSONArray();

			List<Record> records = service.getRecord();

			for (Record record : records) {
				JSONObject recordJSON = new JSONObject();
				recordJSON.put("billingCycle", record.getBillingCycle());
				recordJSON.put("startDate", record.getStartDate());
				recordJSON.put("endDate", record.getEndDate());
				recordJSON.put("strSDate", record.getsDate());
				recordJSON.put("strEDate", record.geteDate());
				recordJSON.put("customerFirstName",
						record.getCustomerFirstName());
				recordJSON
						.put("customerLastName", record.getCustomerLastName());
				recordJSON.put("accountName", record.getAccountName());
				recordJSON.put("amount", record.getAmount());
				arr.put(recordJSON);
			}

			obj.put("records", arr);

			if (records.isEmpty()) {
				obj.put("msg", "No result found.");
				return Response.status(200).entity(obj.toString()).build();
			} else {
				obj.put("msg", "Successfully processed Request File.");
				return Response.status(200).entity(obj.toString()).build();
			}

		} catch (Exception e) {
			Logger.getLogger(RequestResource.class).error(e.getMessage());
			return Response.status(200).entity(e.getMessage()).build();
		}

	}

}
