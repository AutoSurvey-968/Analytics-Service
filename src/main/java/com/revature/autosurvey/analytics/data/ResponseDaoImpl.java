package com.revature.autosurvey.analytics.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.autosurvey.analytics.beans.Response;
import com.revature.autosurvey.analytics.beans.Response.WeekNum;

import reactor.core.publisher.Flux;

@Component
public class ResponseDaoImpl implements ResponseDao {
	
	@Autowired
	private WebClient.Builder webClient;
	
	@Autowired
	private Environment env;

	@Override
	public Flux<Response> getResponses(String surveyId) {
//		WebClient wc = webClient.baseUrl(env.getProperty("GATEWAY_URL")).build();
//		Flux<Response> response = wc.get()
//				.uri(uriBuilder -> uriBuilder.path("/responses/{surveyId}")
//				.build(surveyId))
//				.retrieve()
//				.bodyToFlux(Response.class);
//		return response;
		Map<String, String> surveyResponse1 = new HashMap<>();
		surveyResponse1.put("test", "1");
		Response testResponse1 = new Response(
				UUID.randomUUID(),
				"test batchname", 
				WeekNum.A, 
				UUID.randomUUID(), 
				surveyResponse1);
		
		Map<String, String> surveyResponse2 = new HashMap<>();
		surveyResponse2.put("test", "2");
		Response testResponse2 = new Response(
				UUID.randomUUID(), 
				"test batchname", 
				WeekNum.A, 
				UUID.randomUUID(), 
				surveyResponse2);
		
		
		return Flux.just(testResponse1, testResponse2);

	}

	@Override
	public Flux<Response> getResponses(String surveyId, String weekEnum) {
		WebClient wc = webClient.baseUrl(env.getProperty("GATEWAY_URL")).build();
		Flux<Response> response = wc.get()
				.uri(uriBuilder -> uriBuilder.path("/responses/{surveyId}/{weekEnum}")
				.build(surveyId))
				.retrieve()
				.bodyToFlux(Response.class);
		return response;
	}

}
