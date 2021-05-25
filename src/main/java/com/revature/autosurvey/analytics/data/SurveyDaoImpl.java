package com.revature.autosurvey.analytics.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.autosurvey.analytics.beans.Survey;

import reactor.core.publisher.Mono;

@Component
public class SurveyDaoImpl implements SurveyDao {
	
	@Autowired
	private WebClient.Builder webClient;
	
	@Autowired
	private Environment env;

	@Override
	public Mono<Survey> getSurvey(String surveyId) {
		WebClient wc = webClient.baseUrl(env.getProperty("GATEWAY_URL")).build();
		Mono<Survey> survey = wc.get()
				.uri(uriBuilder -> uriBuilder.path("/survey/{surveyId}")
				.build(surveyId))
				.retrieve()
				.bodyToMono(Survey.class);
		return survey;

	}

}
