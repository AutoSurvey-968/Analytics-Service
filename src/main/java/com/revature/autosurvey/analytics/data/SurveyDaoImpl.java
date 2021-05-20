package com.revature.autosurvey.analytics.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.autosurvey.analytics.beans.Report;

import net.minidev.json.JSONObject;
import reactor.core.publisher.Mono;

@Component
public class SurveyDaoImpl implements SurveyDao {
	
	@Autowired
	private WebClient.Builder webClient;
	
	@Autowired
	private Environment env;

	@Override
	public Mono<Report> getSurvey(String surveyId) {
		WebClient wc = webClient.baseUrl(env.getProperty("GATEWAY_URL")).build();
		Mono<JSONObject> job = wc.get().uri("/survey").retrieve().bodyToMono(JSONObject.class); // Change to report later?
		return job.map(data -> new Report(data.toString()));
	}

}
