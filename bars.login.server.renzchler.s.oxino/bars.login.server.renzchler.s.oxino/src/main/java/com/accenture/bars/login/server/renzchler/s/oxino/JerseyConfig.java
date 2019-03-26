package com.accenture.bars.login.server.renzchler.s.oxino;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.accenture.bars.login.server.renzchler.s.oxino.logincontroller.LoginController;
import com.accenture.bars.login.server.renzchler.s.oxino.user.UserResource;

@Configuration
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig(){
		super();
		register(UserResource.class);
		register(LoginController.class);
	}

}
