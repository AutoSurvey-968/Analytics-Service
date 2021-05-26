package com.revature.autosurvey.analytics.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.autosurvey.analytics.beans.Response;
import com.revature.autosurvey.analytics.beans.Response.WeekEnum;

import reactor.core.publisher.Flux;

@Component
public class ResponseDaoImpl implements ResponseDao {
	
	@Autowired
	private WebClient.Builder webClient;
	
	@Autowired
	private Environment env;

	@Override
	public Flux<Response> getResponses(String surveyId) {
		WebClient wc = webClient.baseUrl(env.getProperty("GATEWAY_URL")).build();
		Flux<Response> response = wc.get()
				.uri((bui) -> 
					bui.pathSegment("/responses").queryParam("surveyId", surveyId).build())
				.retrieve()
				.bodyToFlux(Response.class);
		return response;
	}

	@Override
	public Flux<Response> getResponses(String surveyId, WeekEnum weekEnum) {
		WebClient wc = webClient.baseUrl(env.getProperty("GATEWAY_URL")).build();
		Flux<Response> response = wc.get()
				.uri((bui) -> 
					bui.pathSegment("/responses")
					.queryParam("surveyId", surveyId)
					.queryParam("weekEnum", weekEnum)
					.build())
				.retrieve()
				.bodyToFlux(Response.class);
		return response;
	}

}
