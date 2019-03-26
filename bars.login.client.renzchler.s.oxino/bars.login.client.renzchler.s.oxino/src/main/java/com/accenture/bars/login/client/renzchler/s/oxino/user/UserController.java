package com.accenture.bars.login.client.renzchler.s.oxino.user;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@RestController
public class UserController {

	// http://localhost:3000/register
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Response register(@QueryParam("username") String username,
			@QueryParam("password") String password,
			@QueryParam("roleId") Integer roleId) throws JSONException{

		String url;
		Client client;
		WebResource wr;
		ClientResponse response;
		JSONObject request;

		if (username.isEmpty() || password.isEmpty() || (roleId == 0)) {

			return Response.status(406)
					.entity("Please fill all required fields.")
					.type(MediaType.APPLICATION_JSON).build();

		} else if ((username.length() < 6) || (password.length() < 6)
				|| (roleId > 2)) {

			return Response.status(406).entity("Username and Password must be 6 characters minimum.")
					.type(MediaType.APPLICATION_JSON).build();

		}else{

			url = "http://localhost:8080/server/validate-user";
			client = Client.create();
			wr = client.resource(url);
			request = new JSONObject();
			request.put("username", username);
			response = wr.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request.toString());

			if (response.getStatus() != 200) {
				return Response.status(409).entity("Username already exists.")
						.type(MediaType.APPLICATION_JSON).build();
			}else{
				url = "http://localhost:8080/server/add-user";
				client = Client.create();
				wr = client.resource(url);

				request = new JSONObject();

				request.put("username", username);
				request.put("password", password);
				request.put("roleId", roleId);

				response = wr.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request.toString());

				if (response.getStatus() == 204) {
					return Response.status(200).entity("User has been successfully registerd")
							.type(MediaType.APPLICATION_JSON).build();
				}else{
					return Response.status(500).entity("Unable to process request. Please contact your System Administrator.")
							.type(MediaType.APPLICATION_JSON).build();
				}
			}
		}

	}

	//http://localhost:3000/update
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@QueryParam("id") Integer id, @QueryParam("username") String username,
			@QueryParam("password") String password,
			@QueryParam("roleId") Integer roleId) throws JSONException{

		String url;
		Client client;
		WebResource wr;
		ClientResponse response;
		JSONObject request;

		if (username.isEmpty() || password.isEmpty() || (roleId == 0)) {

			return Response.status(406)
					.entity("Please fill all required fields.")
					.type(MediaType.APPLICATION_JSON).build();

		} else if ((username.length() < 6) || (password.length() < 6)
				|| (roleId > 2)) {

			return Response.status(406).entity("Username and Password must be 6 characters minimum.")
					.type(MediaType.APPLICATION_JSON).build();

		}else{

			url = "http://localhost:8080/server/validate-user";
			client = Client.create();
			wr = client.resource(url);
			request = new JSONObject();
			request.put("username", username);
			response = wr.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, request.toString());

			if (response.getStatus() != 200) {
				return Response.status(409).entity("Username already exists.")
						.type(MediaType.APPLICATION_JSON).build();
			}else{

				url = "http://localhost:8080/server/edit-user";
				client = Client.create();
				wr = client.resource(url);

				request = new JSONObject();

				request.put("id", id);
				request.put("username", username);
				request.put("password", password);
				request.put("roleId", roleId);

				response = wr.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, request.toString());

				if (response.getStatus() == 204) {
				return Response.status(200).entity("User has been successfully updated.")
						.type(MediaType.APPLICATION_JSON).build();
				}else{
				return Response.status(500).entity("Unable to process request. Please contact your System Administrator.")
						.type(MediaType.APPLICATION_JSON).build();
				}
			}
		}

	}

	//http://localhost:3000/update-password-only
	@RequestMapping(value = "/update-password-only", method = RequestMethod.PUT)
	@ResponseBody
	public Response updatePasswordOnly(@QueryParam("id") Integer id, @QueryParam("username") String username,
			@QueryParam("password") String password,
			@QueryParam("roleId") Integer roleId) throws JSONException{

		String url;
		Client client;
		WebResource wr;
		ClientResponse response;
		JSONObject request;

		if (username.isEmpty() || password.isEmpty() || (roleId == 0)) {

			return Response.status(406)
					.entity("Please fill all required fields.")
					.type(MediaType.APPLICATION_JSON).build();

		} else if ((username.length() < 6) || (password.length() < 6)
				|| (roleId > 2)) {

			return Response.status(406).entity("Username and Password must be 6 characters minimum.")
					.type(MediaType.APPLICATION_JSON).build();

		}else{

			url = "http://localhost:8080/server/edit-user";
			client = Client.create();
			wr = client.resource(url);

			request = new JSONObject();

			request.put("id", id);
			request.put("username", username);
			request.put("password", password);
			request.put("roleId", roleId);

			response = wr.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, request.toString());

			if (response.getStatus() == 204) {
			return Response.status(200).entity("User has been successfully updated.")
					.type(MediaType.APPLICATION_JSON).build();
			}else{
			return Response.status(500).entity("Unable to process request. Please contact your System Administrator.")
					.type(MediaType.APPLICATION_JSON).build();
			}

		}

	}

	//http://localhost:3000/delete-user
	@RequestMapping(value="/delete-user", method = RequestMethod.DELETE)
	@ResponseBody
	public Integer deleteUser(@QueryParam("id") Integer id) throws JSONException{
		return id;
	}

}
