package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClientController {
	
	@Autowired
	private OAuth2ResourceDetails resourceDetails;
 
	@RequestMapping(value = "/fetchMessage1", method = RequestMethod.GET)
    public @ResponseBody String getMessageFromResource1(){
		Resources resource = resourceDetails.getOAuth2RT().getForObject("http://localhost:8080/resource1/xyz", Resources.class);
        return resource.getValue(); 
    } 
	 
	@RequestMapping(value = "/fetchMessage2", method = RequestMethod.GET)
    public @ResponseBody String getMessageFromResource2(){
		Resources resource = resourceDetails.getOAuth2RT().getForObject("http://localhost:8080/resource1/abc", Resources.class);
        return resource.getValue(); 
    } 
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		return "index";
	} 
}