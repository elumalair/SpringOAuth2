package com.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

import com.client.ClientDetailsService;
import com.db.AuthDataSource;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	private final Log log = LogFactory.getLog(getClass());
	
	@Autowired
    private AuthDataSource authDataSource;
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	 
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		log.debug("--- endpoints invoked ---");
		endpoints.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		log.debug("--- clients invoked ---");

		//Using Custom table
		clients.withClientDetails(new ClientDetailsService(authDataSource.dataSource()));
	} 
}