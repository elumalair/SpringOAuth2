package com;

import java.util.UUID;

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
public class SpringBootRes1WebApplication extends SpringBootServletInitializer {

	private final static Log log = LogFactory.getLog(SpringBootRes1WebApplication.class);

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootRes1WebApplication.class);
    }

    public static void main(String[] args) throws Exception {
    	log.debug("---  Oauth2AuthorizationServer -- Started ---");
        SpringApplication.run(SpringBootRes1WebApplication.class, args);
    }
    
    @RequestMapping("/abc")
	public Resources securedCall() {
    	Resources resources = new Resources();
    	resources.setValue("Success (Resource ABC: " + UUID.randomUUID().toString().toUpperCase() + ")");
		return resources;
	}
    
    @RequestMapping("/xyz")
   	public Resources securedCall1() {
    	Resources resources = new Resources();
    	resources.setValue("Success (Resource XYZ: " + UUID.randomUUID().toString().toUpperCase() + ")");
		return resources;
   	}
}