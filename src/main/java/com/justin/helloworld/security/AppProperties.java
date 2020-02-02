package com.justin.helloworld.security;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class AppProperties {
	
	@Autowired	
	private Environment env;
	
	public String getTokenSecret() {
		return env.getProperty("tokenSecret");
	}

}
