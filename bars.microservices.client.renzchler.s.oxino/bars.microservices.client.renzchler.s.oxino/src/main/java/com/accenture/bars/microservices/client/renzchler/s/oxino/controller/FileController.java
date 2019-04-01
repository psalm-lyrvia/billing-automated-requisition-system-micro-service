package com.accenture.bars.microservices.client.renzchler.s.oxino.controller;

import java.text.ParseException;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.accenture.bars.microservices.client.renzchler.s.oxino.BarsRibbonConfiguration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@EnableCircuitBreaker
@Controller
@RibbonClient(name="bars-server", configuration=BarsRibbonConfiguration.class)
public class FileController {

	//loggger
	private static Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private BarsService bs;

	@LoadBalanced
	@GetMapping("/testcb")
	public String testcb(){

		String url = "http://bars-server/record/get-records";
		Client client = new Client();
		WebResource wr = client.resource(url);
		ClientResponse response = wr.type(MediaType.APPLICATION_JSON).get(
				ClientResponse.class);

		return "success";
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("/upload")
	// //new annotation since 4.3
	public String requestUpload(@RequestParam("file") MultipartFile file,
			Model model) throws JSONException, ParseException {
		return bs.barsUpload(file, model);
	}

	@PostMapping("/success")
	public String uploadStatus() {
		return "success";
	}
}