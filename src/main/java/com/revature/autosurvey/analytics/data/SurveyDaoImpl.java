package com.revature.autosurvey.analytics.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.autosurvey.analytics.beans.Question;
import com.revature.autosurvey.analytics.beans.Question.QuestionType;
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
//		WebClient wc = webClient.baseUrl(env.getProperty("GATEWAY_URL")).build();
//		Mono<Survey> survey = wc.get()
//				.uri(uriBuilder -> uriBuilder.path("/survey/{surveyId}")
//				.build(surveyId))
//				.retrieve()
//				.bodyToMono(Survey.class);
//		return survey;
		List<Question> questionsList = new ArrayList<Question>();
		Question question = new Question();
		question.setQuestionType(QuestionType.MULTIPLE_CHOICE);
		question.setTitle("test");
		question.setHelpText("test help text");
		question.setIsRequired(true);
		List<String> choices = new ArrayList<String>();
		choices.add("1");
		choices.add("2");
		question.setChoices(choices);
		question.setHasOtherOption(false);
		questionsList.add(question);
		return Mono.just(new Survey(
				UUID.randomUUID(), 
				LocalDateTime.now(), 
				"test survey title", 
				"test description", 
				"test confirmation", 
				"test version", 
				new ArrayList<String>(), 
				questionsList)
				);
	}

}
