package com.accenture.bars.login.client.renzchler.s.oxino.user;

public class User {

	private int id;

	private String username;

	private String password;

	private int roleId;

	public User(){
		super();
	}

	public User(int id, String username, String password, int roleId){
		this.id = id;
		this.username = username;
		this.password = password;
		this.roleId = roleId;
	}

	public User(String username, String password, int roleId){
		this.username = username;
		this.password = password;
		this.roleId = roleId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


}
