package com.accenture.bars.microservices.server.renzchler.s.oxino.billing;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BillingService {

	@Autowired
	private IBillingRepository repo;

	public List<Billing> getAllBilling() {
		List<Billing> billing = new ArrayList<>();
		repo.findAll().forEach(billing::add);
		return billing;
	}

	public List<Billing> getCustomerBilling(String request)
			throws ParseException, JSONException {

		List<Billing> billing = new ArrayList<>();
		JSONArray arr = new JSONArray(request);

		for (int i = 0; i < arr.length(); i++) {
			repo.findAllByBillingCycleAndStartDateAndEndDate(
					arr.getJSONObject(i).getInt("billingCycle"),
					arr.getJSONObject(i).getString("startDate"),
					arr.getJSONObject(i).getString("endDate")).forEach(
					billing::add);
		}

		return billing;
	}

	public Collection<Billing> getBillingAccount(int id){
		return repo.findByAccountAccountId(id);
	}



}
