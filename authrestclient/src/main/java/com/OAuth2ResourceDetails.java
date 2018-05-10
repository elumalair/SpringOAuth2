package com;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

@Configuration
public class OAuth2ResourceDetails {

	@Value("${security.oauth2.client.clientId}")
	private String clientId;

	@Value("${security.oauth2.client.clientSecret}")
	private String clientSecret;

	@Value("${security.oauth2.client.accessTokenUri}")
	private String accessTokenUri;

	@Value("${security.oauth2.client.userAuthorizationUri}")
	private String userAuthorizationUri;
	
	@Value("${security.oauth2.client.username}")
	private String username;
	
	@Value("${security.oauth2.client.password}")
	private String password;

	 
	public OAuth2RestTemplate getOAuth2RT() {
  		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
	    resourceDetails.setUsername(username);
	    resourceDetails.setPassword(password);
	    resourceDetails.setAccessTokenUri(accessTokenUri);
	    resourceDetails.setClientId(clientId);
	    resourceDetails.setClientSecret(clientSecret);
	    resourceDetails.setGrantType("password");
	    DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
	    OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
	    restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
		return restTemplate;
	} 
}