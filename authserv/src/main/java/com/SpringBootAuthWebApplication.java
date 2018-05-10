package com.cnsi;

import java.security.Principal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableResourceServer
public class SpringBootAuthWebApplication extends SpringBootServletInitializer {

	private final static Log log = LogFactory.getLog(SpringBootAuthWebApplication.class);

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootAuthWebApplication.class);
    }

    public static void main(String[] args) throws Exception {
    	log.debug("---  Oauth2AuthorizationServer -- Started ---");
        SpringApplication.run(SpringBootAuthWebApplication.class, args);
    }
    
    @RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
}