package com.accenture.bars.microservices.client.renzchler.s.oxino.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.accenture.bars.microservices.client.renzchler.s.oxino.BarsRibbonConfiguration;
import com.accenture.bars.microservices.client.renzchler.s.oxino.domain.Record;
import com.accenture.bars.microservices.client.renzchler.s.oxino.exception.BarsException;
import com.accenture.bars.microservices.client.renzchler.s.oxino.file.BarsXMLUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RibbonClient(name = "bars-server", configuration = BarsRibbonConfiguration.class)
@Service
public class BarsService {

	@Autowired
	private RestTemplate restTemplate;

	// loggger
	private static Logger logger = LoggerFactory
			.getLogger(FileController.class);

	// Save the uploaded file to this folder
	private static String DIR_PATH = "C:\\Java_Spring_Boot_Micro_Service_RSO"
			+ "\\bars.microservices.client.renzchler.s.oxino"
			+ "\\bars.microservices.client.renzchler.s.oxino"
			+ "\\src\\main\\resources\\static\\tempFiles\\";

	public BarsService() {
		super();
	}

	@HystrixCommand(fallbackMethod = "barsUploadFallback")
	public String barsUpload(MultipartFile file, Model model)
			throws JSONException, ParseException {

		if (file.isEmpty()) {
			model.addAttribute("msg", BarsException.NO_RECORDS_TO_READ);
		}

		try {
			FileProcessor fileProcessor = new FileProcessor();

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			String tempFile = DIR_PATH + file.getOriginalFilename();
			Path path = Paths.get(tempFile);
			Files.write(path, bytes);

			// get temp file
			File requestFile = new File(tempFile);

			String request = fileProcessor.execute(requestFile);

			// setup header
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> requestEntity;

			ResponseEntity<String> response;

			String url;

			url = "http://bars-server/add-request";
			requestEntity = new HttpEntity<>(request, headers);

			response = this.restTemplate.postForEntity(url, requestEntity,
					String.class);

			// check if request is save
			if (response.getStatusCodeValue() == 200) {

				url = "http://bars-server/get-records";

				requestEntity = new HttpEntity<>(request, headers);

				response = this.restTemplate.getForEntity(url, String.class);

				// retrieve records
				if (response.getStatusCodeValue() == 200) {

					String recordsResponse = response.getBody();

					// Convert string json array to list object
					List<Record> records = fileProcessor
							.getRecords(recordsResponse);

					// generate XML
					boolean isGenerated = BarsXMLUtils.generateXML(records);

					if (isGenerated) {

						// refresh request table
						url = "http://bars-server/delete-request";

						requestEntity = new HttpEntity<>(request, headers);

						this.restTemplate.delete(url);

						// delete temporary files
						fileProcessor.clearDir(DIR_PATH);

						model.addAttribute("message", recordsResponse);

						return "/success";

					} else {
						model.addAttribute(
								"msg", BarsException.NO_RECORDS_TO_WRITE);
					}

				} else {
					model.addAttribute("msg", BarsException.NO_RECORDS_TO_READ);
				}

			} else {
				model.addAttribute("msg",
						BarsException.NO_RECORDS_TO_WRITE);
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (NullPointerException e) {
			logger.error(e.getMessage());
			model.addAttribute("msg", BarsException.NO_SUPPORTED_FILE);
		} catch (BarsException e) {
			logger.error(e.getMessage());
			model.addAttribute("msg", e.getMessage());
		}

		return "index";
	}

	public String barsUploadFallback(MultipartFile file, Model model) {
		return "barsFallback";
	}

}
