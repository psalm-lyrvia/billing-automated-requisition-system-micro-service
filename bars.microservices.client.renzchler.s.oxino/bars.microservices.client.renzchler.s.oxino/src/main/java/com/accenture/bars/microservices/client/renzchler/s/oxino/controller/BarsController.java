package com.accenture.bars.microservices.client.renzchler.s.oxino.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BarsController {

	@GetMapping("/index")
	public String index(){
		return "index";
	}

	@GetMapping("/header")
	public String getHeader(){
		return "header";
	}

	@GetMapping("/home")
	public String getHome(){
		return "views/home";
	}

	@GetMapping("/directory")
	public String getDir(){
		return "views/directory";
	}
}
