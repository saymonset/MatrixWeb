package com.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.packtpub.wflydevelopment.chapter7.boundary.AccountResource;
import com.packtpub.wflydevelopment.chapter7.boundary.SeatsResource;

@ApplicationPath("/services")
public class RestApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public RestApplication() {
	 
		singletons.add(new SeatsResource());
		singletons.add(new AccountResource());
		empty.add(SeatsResource.class);
		empty.add(AccountResource.class);
		
		

	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}