package com.bridgelabz.fundoo.applicationconfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration 
{
//	@Bean
//	public PasswordEncoder passwordEncoder()
//	{
//		return new BCryptPasswordEncoder();
//	}

	//	@Bean
	//	public static PropertySourcesPlaceholderConfigurer Property()
	//	{
	//		return new PropertySourcesPlaceholderConfigurer();
	//	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
//	@Bean
//	public Response getResponse()
//	{
//		return new Response();
//	}
	
	
	// Defining bean for rest high level client
//	@Bean(destroyMethod = "close")
//	@Bean
//	public RestHighLevelClient client() {
//		
//		RestHighLevelClient client = new RestHighLevelClient(
//				RestClient.builder(new HttpHost("localhost", 9200, "http")));
//
//		return client;
//
//	}
	
//	@Bean
//	public RestHighLevelClientConfiguration client()
//	{
//		return new RestHighLevelClientConfiguration();
//	}
	
////	
//	@Bean
//	public UserToken getToken()
//	{
//		return new UserToken();
//	}
//	@Bean
//	public ResponseToken getResponseToken()
//	{
//		return new ResponseToken();
//	}
}
