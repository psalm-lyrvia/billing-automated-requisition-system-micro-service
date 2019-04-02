package com.accenture.bars.microservices.client.renzchler.s.oxino.controller;

import java.text.ParseException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@EnableCircuitBreaker
@Controller
public class FileController {

	@Autowired
	private BarsService bs;

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
