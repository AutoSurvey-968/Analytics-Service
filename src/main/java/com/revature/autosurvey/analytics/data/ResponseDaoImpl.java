package com.revature.autosurvey.analytics.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.autosurvey.analytics.beans.Response;

import reactor.core.publisher.Flux;

@Component
public class ResponseDaoImpl implements ResponseDao {
	
	@Autowired
	private WebClient.Builder webClient;

	@Override
	public Flux<Response> getResponses(String surveyId) {
		WebClient wc = webClient.baseUrl(System.getenv("GATEWAY_URL")).build();
		Flux<Response> response = wc.get()
				.uri(builder -> 
					builder.pathSegment("/responses").queryParam("surveyId", surveyId).build())
				.retrieve()
				.bodyToFlux(Response.class);
		return response;
	}

	@Override
	public Flux<Response> getResponses(String surveyId, String weekDay) {
		WebClient wc = webClient.baseUrl(System.getenv("GATEWAY_URL")).build();
		return wc.get()
				.uri(builder -> 
					builder.pathSegment("/responses")
					.queryParam("surveyId", surveyId)
					.queryParam("weekDay", weekDay)
					.build())
				.retrieve()
				.bodyToFlux(Response.class);
	}

	@Override
	public Flux<Response> getResponses(String surveyId, String weekDay, String batchName) {
		WebClient wc = webClient.baseUrl(System.getenv("GATEWAY_URL")).build();
		return wc.get()
				.uri(builder -> 
					builder.pathSegment("/responses")
					.queryParam("surveyId", surveyId)
					.queryParam("weekDay", weekDay)
					.queryParam("batchName", batchName)
					.build())
				.retrieve()
				.bodyToFlux(Response.class);
	}

}
