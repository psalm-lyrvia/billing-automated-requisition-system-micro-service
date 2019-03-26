package com.accenture.bars.login.server.renzchler.s.oxino.user;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;


@Path("server")
public class UserResource {

	@Autowired
	private UserService userService;

	@GET
	@Path("testresponse")
	public String responseFromServer() {
		return "Advance na kami";
	}

	//http://localhost:8080/server/users
	@GET
	@Path("users")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}

	//http://localhost:8080/server/add-user
	@POST
	@Path("add-user")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addUser(User user){
		userService.addUser(user);
	}

	//http://localhost:8080/server/get-user/{id}
	@GET
	@Path("get-user/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") int id){
		return userService.getUser(id);
	}

	//http://localhost:8080/server/edit-user
	@PUT
	@Path("edit-user")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(User user){
		userService.updateUser(user);
	}

	//http://localhost:8080/server/delete-user
	@DELETE
	@Path("delete-user")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteUser(User user){
		userService.deleteUser(user);
	}

	//http://localhost:8080/server/validate-user
	@POST
	@Path("validate-user")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validateUser(User user){
		if (userService.validateUser(user)) {
			return Response.status(404).entity("User already exists.")
					.type(MediaType.APPLICATION_JSON).build();
		}else{
			return Response.status(200).entity("Passed.")
					.type(MediaType.APPLICATION_JSON).build();
		}

	}



}
