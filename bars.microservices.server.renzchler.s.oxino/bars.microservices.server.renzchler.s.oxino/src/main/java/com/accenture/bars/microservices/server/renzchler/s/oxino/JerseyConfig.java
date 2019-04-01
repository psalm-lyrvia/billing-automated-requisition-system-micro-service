package com.accenture.bars.microservices.server.renzchler.s.oxino;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.accenture.bars.microservices.server.renzchler.s.oxino.account.AccountResource;
import com.accenture.bars.microservices.server.renzchler.s.oxino.billing.BillingResource;
import com.accenture.bars.microservices.server.renzchler.s.oxino.customer.CustomerResource;
import com.accenture.bars.microservices.server.renzchler.s.oxino.record.RecordResource;
import com.accenture.bars.microservices.server.renzchler.s.oxino.request.RequestResource;

@Configuration
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig(){
		super();
		register(CustomerResource.class);
		register(AccountResource.class);
		register(RequestResource.class);
		register(BillingResource.class);
		register(RecordResource.class);
	}

}
