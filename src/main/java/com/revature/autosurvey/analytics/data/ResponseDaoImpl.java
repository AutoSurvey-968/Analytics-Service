package com.revature.autosurvey.analytics.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.autosurvey.analytics.beans.Response;

import karate.com.linecorp.armeria.common.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ResponseDaoImpl implements ResponseDao {
	
	@Autowired
	private WebClient.Builder webClient;

	@Override
	public Flux<Response> getResponses(String surveyId) {
		WebClient wc = webClient.baseUrl(System.getenv("GATEWAY_URL")).build();
		Flux<Response> response = wc.get()
				.uri(builder -> 
					builder.pathSegment("responses").queryParam("surveyId", surveyId).build())
				.exchangeToFlux(res -> {
					if (res.rawStatusCode() == 200) {
						return res.bodyToFlux(Response.class)
								.switchIfEmpty(Flux.just(new Response(surveyId)));
					}
					return Flux.just(new Response(""));
				});
		return response;
	}

	@Override
	public Flux<Response> getResponses(String surveyId, String weekDay) {
		WebClient wc = webClient.baseUrl(System.getenv("GATEWAY_URL")).build();
		return wc.get()
				.uri(builder -> 
					builder.pathSegment("responses")
					.queryParam("id", surveyId)
					.queryParam("weekStart", weekDay)
					.build())
				.exchangeToFlux(res -> {
					if (res.rawStatusCode() == 200) {
						return res.bodyToFlux(Response.class)
								.switchIfEmpty(Flux.just(new Response(surveyId)));
					}
					return Flux.just(new Response(""));
				});
	}

	@Override
	public Flux<Response> getResponses(String surveyId, String weekDay, String batchName) {
		WebClient wc = webClient.baseUrl(System.getenv("GATEWAY_URL")).build();
		return wc.get()
				.uri(builder -> 
					builder.pathSegment("responses")
					.queryParam("id", surveyId)
					.queryParam("weekStart", weekDay)
					.queryParam("batch", batchName)
					.build())
				.exchangeToFlux(res -> {
					if (res.rawStatusCode() == 200) {
						return res.bodyToFlux(Response.class)
								.switchIfEmpty(Flux.just(new Response(surveyId)));
					}
					return Flux.just(new Response(""));
				});
	}

}
