package com.accenture.bars.microservices.server.renzchler.s.oxino.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

	@Autowired
	private IAccountRepository repo;

	public Account getAccount(int id){
		return repo.findOne(id);
	}

}
