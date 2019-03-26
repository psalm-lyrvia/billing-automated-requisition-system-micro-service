package com.accenture.bars.login.server.renzchler.s.oxino.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {

	User findByUsernameAndPassword(String username, String password);

	User findByUsername(String username);
}
