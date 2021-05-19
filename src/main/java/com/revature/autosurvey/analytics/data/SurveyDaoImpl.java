package com.revature.autosurvey.analytics.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import net.minidev.json.JSONObject;
import reactor.core.publisher.Flux;

@Component
public class SurveyDaoImpl implements SurveyDao {
	
	@Autowired
	private WebClient.Builder webClient;
	
	@Autowired
	private Environment env;

	@Override
	public Flux<JSONObject> getSurvey(String surveyId) {
		WebClient wc = webClient.baseUrl(env.getProperty("GATEWAY_URL")).build();
		Flux<JSONObject> job = wc.get().uri("/survey").retrieve().bodyToFlux(JSONObject.class);
		return job;
	}

}
