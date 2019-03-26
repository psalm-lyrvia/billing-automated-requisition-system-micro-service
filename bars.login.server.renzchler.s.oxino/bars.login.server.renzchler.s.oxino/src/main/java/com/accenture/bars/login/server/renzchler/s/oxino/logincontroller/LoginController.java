package com.accenture.bars.login.server.renzchler.s.oxino.logincontroller;

import java.util.Base64;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.bars.login.server.renzchler.s.oxino.user.IUserRepository;
import com.accenture.bars.login.server.renzchler.s.oxino.user.User;

@Path("auth")
public class LoginController {

	@Autowired
	private IUserRepository repo;
	private String username;
	private String password;
	private String message;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private User user;

	// http://localhost:8080/auth/verify
	@GET
	@Path("verify")
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(@HeaderParam("Authorization") String authString) {

		if (isUserAuthenticated(authString)) {
			return Response.status(200)
					.entity(getUser())
					.type(MediaType.APPLICATION_JSON).build();
		}else{
			return Response.status(404)
					.entity(getMessage())
					.type(MediaType.APPLICATION_JSON).build();
		}


	}

	private boolean isUserAuthenticated(String authString) {

		String[] authParts = authString.split("\\s+");
		String authInfo = authParts[0];
		byte[] bytes = Base64.getDecoder().decode(authInfo);
		String decodedAuth = new String(bytes);
		String[] credentials = decodedAuth.split(":");
		String username = credentials[0];
		String password = credentials[1];

		try {
			User result = repo.findByUsernameAndPassword(username, password);
			setUsername(result.getUsername());
			setPassword(result.getPassword());
			if (result.getUsername().equals(getUsername()) && result.getPassword().equals(getPassword())) {
				setUser(result);
				return true;
			}else{
				return false;
			}
		} catch (NullPointerException e) {
			setMessage("Please check username or password");
			return false;
		}


	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String string) {
		this.message = string;
	}


}
