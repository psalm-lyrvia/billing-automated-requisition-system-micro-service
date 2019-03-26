package com.accenture.bars.login.server.renzchler.s.oxino.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private IUserRepository userRepository;

	public List<User> getAllUsers(){
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}

	public void addUser(User user){
		userRepository.save(user);
	}

	public User getUser(int id){
		return userRepository.findOne(id);
	}

	public void updateUser(User user){
		userRepository.save(user);
	}

	public void deleteUser(User user){
		userRepository.delete(user);
	}

	public boolean validateUser(User user){
		User result = userRepository.findByUsername(user.getUsername());
		if (result != null) {
			return true;
		}else{
			return false;
		}
	}

}
