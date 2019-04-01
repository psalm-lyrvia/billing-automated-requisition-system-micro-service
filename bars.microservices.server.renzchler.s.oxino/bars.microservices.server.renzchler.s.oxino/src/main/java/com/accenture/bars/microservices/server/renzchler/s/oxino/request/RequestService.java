package com.accenture.bars.microservices.server.renzchler.s.oxino.request;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

	@Autowired
	private IRequestRepository repo;

	public List<Request> getAllRequest() {
		List<Request> request = new ArrayList<>();
		repo.findAll().forEach(request::add);
		return request;
	}

	public void addRequest(String request) throws JSONException, ParseException {

		JSONObject json = new JSONObject(request);

		JSONArray arr= json.getJSONArray("request");

		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

		List<Request> req = new ArrayList<>();

		for (int i = 0; i < arr.length(); i++) {

			Date sdate = (Date)formatter.parse(arr.getJSONObject(i).getString("startDate"));
			Date edate = (Date)formatter.parse(arr.getJSONObject(i).getString("endDate"));

			Calendar sCal = Calendar.getInstance();
			sCal.setTime(sdate);
			Calendar eCal = Calendar.getInstance();
			eCal.setTime(edate);

			String strSDate = sCal.get(Calendar.YEAR) + "-"
					+ (sCal.get(Calendar.MONTH) + 1) + "-"
					+ sCal.get(Calendar.DATE);
			String strEDate = eCal.get(Calendar.YEAR) + "-"
					+ (eCal.get(Calendar.MONTH) + 1) + "-"
					+ eCal.get(Calendar.DATE);

			req.add(new Request(arr.getJSONObject(i).getInt("billingCycle"), strSDate, strEDate));
		}

		for (Request reqList : req) {
			repo.save(reqList);
		}

	}

	public void deleteAllRequest(){
		repo.deleteAll();
	}



}
