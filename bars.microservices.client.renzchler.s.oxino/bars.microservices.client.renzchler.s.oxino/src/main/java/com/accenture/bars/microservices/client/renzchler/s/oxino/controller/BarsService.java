package com.accenture.bars.microservices.client.renzchler.s.oxino.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.accenture.bars.microservices.client.renzchler.s.oxino.domain.Record;
import com.accenture.bars.microservices.client.renzchler.s.oxino.exception.BarsException;
import com.accenture.bars.microservices.client.renzchler.s.oxino.file.BarsXMLUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component
public class BarsService {

	// Save the uploaded file to this folder
	private static String DIR_PATH = "C:\\Java_Spring_Boot_Micro_Service_RSO"
			+ "\\bars.microservices.client.renzchler.s.oxino"
			+ "\\bars.microservices.client.renzchler.s.oxino"
			+ "\\src\\main\\resources\\static\\tempFiles\\";

	public BarsService() {
		super();
	}

	@HystrixCommand(fallbackMethod = "testfb")
	public String testcb() {
		// refresh request table
		String url = "http://localhost:8090/request/delete-request/";
		Client client = new Client();
		WebResource wr = client.resource(url);
		ClientResponse response = wr.type(MediaType.APPLICATION_JSON).delete(
				ClientResponse.class);

		return "success";
	}

	@HystrixCommand(fallbackMethod = "barsUploadFallback")
	public String barsUpload(MultipartFile file, Model model) throws JSONException, ParseException {

		if (file.isEmpty()) {
			model.addAttribute("msg", BarsException.NO_RECORDS_TO_READ);
			return "index";
		}

		try {
			FileProcessor fileProcessor = new FileProcessor();

			String url;

			Client client;

			WebResource wr;

			ClientResponse response;
			//
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			String tempFile = DIR_PATH + file.getOriginalFilename();
			Path path = Paths.get(tempFile);
			Files.write(path, bytes);
			int index = file.getOriginalFilename().lastIndexOf('.');
			String mimeType = file.getOriginalFilename().substring(index + 1);

			// get temp file
			File requestFile = new File(tempFile);

			String request = fileProcessor.execute(requestFile);

			url = "http://localhost:8090/request/add-request";

			client = new Client();

			wr = client.resource(url);

			response = wr.type(MediaType.APPLICATION_JSON).post(
					ClientResponse.class, request);

			// check if request is save
			if (response.getStatus() == 200) {

				url = "http://localhost:8090/record/get-records";
				client = new Client();
				wr = client.resource(url);
				response = wr.type(MediaType.APPLICATION_JSON).get(
						ClientResponse.class);

				// retrieve records
				if (response.getStatus() == 200) {

					String recordsResponse = response.getEntity(String.class);

					// Convert string json array to list object
					List<Record> records = fileProcessor
							.getRecords(recordsResponse);

					// generate XML
					boolean isGenerated = BarsXMLUtils.generateXML(records);

					if (isGenerated) {

						// refresh request table
						url = "http://localhost:8090/request/delete-request/";
						client = new Client();
						wr = client.resource(url);
						response = wr.type(MediaType.APPLICATION_JSON).delete(
								ClientResponse.class);

						// delete temporary files
						fileProcessor.clearDir(DIR_PATH);

						model.addAttribute("message", recordsResponse);

						return "/success";

					} else {
						model.addAttribute(
								"message",
								"File Retrieve '" + file.getOriginalFilename()
										+ "' " + mimeType + "\n"
										+ records.toString());
					}

				} else {
					model.addAttribute("message", "You successfully uploaded '"
							+ file.getOriginalFilename() + "' " + mimeType
							+ "\n" + response.getEntity(String.class));
				}

			} else {
				model.addAttribute(
						"message",
						"Request not saved. '" + file.getOriginalFilename()
								+ "' " + mimeType + "\n"
								+ response.getEntity(String.class));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			// throw new BarsException(BarsException.NO_SUPPORTED_FILE);
			model.addAttribute("msg", BarsException.NO_SUPPORTED_FILE);
		} catch (BarsException e) {
			model.addAttribute("msg", e.getMessage());
		}

		return "index";
	}


	public String barsUploadFallback(MultipartFile file, Model model){
		return "barsFallback";
	}

	public String testfb() {
		return "index";
	}

}
