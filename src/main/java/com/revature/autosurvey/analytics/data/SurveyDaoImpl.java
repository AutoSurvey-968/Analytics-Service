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

	@Override
	public Mono<Survey> getSurvey(String surveyId) {
		WebClient wc = webClient.baseUrl(System.getenv("GATEWAY_URL")).build();
		return wc.get()
				.uri(uriBuilder -> uriBuilder.path("surveys/{surveyId}")
				.build(surveyId))
				.retrieve()
				.bodyToMono(Survey.class);

	}

}
