package com.revature.autosurvey.analytics.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.beans.Response;
import com.revature.autosurvey.analytics.beans.Survey;

import net.minidev.json.JSONObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ResponseDaoImpl implements ResponseDao {
	
	@Autowired
	private WebClient.Builder webClient;
	
	@Autowired
	private Environment env;

	@Override
	public Flux<Response> getResponses(String surveyId) {
		WebClient wc = webClient.baseUrl(env.getProperty("GATEWAY_URL")).build();
		Flux<Response> job = wc.get()
				.uri(uriBuilder -> uriBuilder.path("/responses/{surveyId}")
				.build(surveyId))
				.retrieve()
				.bodyToFlux(Response.class);
		return job;
	}

}
