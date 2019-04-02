package com.accenture.bars.login.client.renzchler.s.oxino.logincontroller;

import java.io.IOException;
import java.util.Base64;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.bars.login.client.renzchler.s.oxino.user.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Controller
public class LoginController {

	// http://localhost:3070/login
	@RequestMapping("/login")
	public String getLoginPage() {
		return "login";
	}

	//http://localhost:3070/register
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ModelAndView authenticate(@QueryParam("username") String username,
			@QueryParam("password") String password, Model model) throws JsonParseException, JsonMappingException, IOException{
		String url = "http://localhost:3000/auth/verify";

		String authString = username + ":" + password;
		// encode
		String authStr = Base64.getEncoder().encodeToString(
				authString.getBytes());

		Client client = Client.create();
		WebResource wr = client.resource(url);
		ClientResponse response = wr.type(MediaType.APPLICATION_JSON)
				.header("Authorization", authStr).get(ClientResponse.class);
		String output = response.getEntity(String.class);

		ModelAndView mav = new ModelAndView();

		ObjectMapper mapper = new ObjectMapper();

		if (response.getStatus() == 200) {
			User user = mapper.readValue(output, User.class);
			mav.addObject("response", user);
			if (user.getRoleId() == 1) {
				mav.setViewName("adminAccount");
			}else{
				mav.setViewName("userAccount");
			}
			return mav;
		} else {
			model.addAttribute("message", output);
			mav.setViewName("login");
			return mav;
		}

	}

}
