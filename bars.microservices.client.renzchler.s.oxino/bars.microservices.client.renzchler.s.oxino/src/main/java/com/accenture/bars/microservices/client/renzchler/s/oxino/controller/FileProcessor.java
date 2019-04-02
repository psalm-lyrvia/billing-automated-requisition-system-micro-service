package com.accenture.bars.microservices.client.renzchler.s.oxino.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.accenture.bars.microservices.client.renzchler.s.oxino.domain.Record;
import com.accenture.bars.microservices.client.renzchler.s.oxino.domain.Request;
import com.accenture.bars.microservices.client.renzchler.s.oxino.exception.BarsException;
import com.accenture.bars.microservices.client.renzchler.s.oxino.factory.InputFileFactory;
import com.accenture.bars.microservices.client.renzchler.s.oxino.file.IInputFile;

public class FileProcessor {
	private IInputFile inputFile;

	public String execute(File file) throws JSONException, BarsException {

		InputFileFactory inputFileFac = InputFileFactory.getInstance();

		inputFile = inputFileFac.getInputFile(file);

		inputFile.setFile(file);

		List<Request> requests = inputFile.readFile();

		// convert to json array
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();

		for (Request request : requests) {
			JSONObject requestJSON = new JSONObject();
			requestJSON.put("billingCycle", request.getBillingCycle());
			requestJSON.put("startDate", request.getStartDate());
			requestJSON.put("endDate", request.getEndDate());
			arr.put(requestJSON);
		}

		obj.put("request", arr);

		return obj.toString();

	}

	public List<Record> getRecords(String records) throws JSONException, ParseException {
		JSONObject json = new JSONObject(records);

		JSONArray arr = json.getJSONArray("records");

		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

		List<Record> record = new ArrayList<>();

		for (int i = 0; i < arr.length(); i++) {

			record.add(new Record());
			record.get(i).setBillingCycle(arr.getJSONObject(i).getInt("billingCycle"));
			record.get(i).setStartDate(formatter.parse(arr.getJSONObject(i).getString("startDate")));
			record.get(i).setEndDate(formatter.parse(arr.getJSONObject(i).getString("endDate")));
			record.get(i).setAccountName(arr.getJSONObject(i).getString("accountName"));
			record.get(i).setAmount(arr.getJSONObject(i).getDouble("amount"));
			record.get(i).setCustomerFirstName(arr.getJSONObject(i).getString("customerFirstName"));
			record.get(i).setCustomerLastName(arr.getJSONObject(i).getString("customerLastName"));
			record.get(i).setsDate(arr.getJSONObject(i).getString("strSDate"));
			record.get(i).seteDate(arr.getJSONObject(i).getString("strEDate"));

		}

		return record;
	}

	public void clearDir(String dir){

		File directory = new File(dir);

		// Get all files in directory

		File[] files = directory.listFiles();

		for (File file : files){

		// Delete each file
		file.delete();

		}
	}

}
