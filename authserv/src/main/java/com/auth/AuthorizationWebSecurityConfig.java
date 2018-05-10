package com.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.db.AuthDataSource;
import com.user.UserDetailsService;

@Configuration
@EnableWebSecurity
public class AuthorizationWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final Log log = LogFactory.getLog(getClass());
	
	@Autowired
    private AuthDataSource authDataSource;
		
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		log.debug("--- SecurityConfig invoked ---");
		userDetailsService.setDataSource(authDataSource.dataSource());
		log.debug("--- Database intialized invoked ---");
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.and()
		.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.permitAll();
	}
}