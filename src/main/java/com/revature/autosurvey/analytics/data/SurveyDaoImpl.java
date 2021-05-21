package com.revature.autosurvey.analytics.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.autosurvey.analytics.beans.Question;
import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.beans.Survey;

import net.minidev.json.JSONObject;
import reactor.core.publisher.Mono;

@Component
public class SurveyDaoImpl implements SurveyDao {
	
	@Autowired
	private WebClient.Builder webClient;
	
	@Autowired
	private Environment env;

	@Override
	public Mono<Survey> getSurvey(String surveyId) {
//		WebClient wc = webClient.baseUrl(env.getProperty("GATEWAY_URL")).build();
//		Mono<Survey> survey = wc.get()
//				.uri(uriBuilder -> uriBuilder.path("/survey/{surveyId}")
//				.build(surveyId))
//				.retrieve()
//				.bodyToMono(Survey.class);
//		return survey;
		return Mono.just(new Survey(
				UUID.randomUUID(), 
				LocalDateTime.now(), 
				"test title", 
				"test description", 
				"test confirmation", 
				"test version", 
				new ArrayList<String>(), 
				new ArrayList<Question>())
				);
	}

}
