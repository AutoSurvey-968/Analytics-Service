package com.revature.autosurvey.analytics.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.autosurvey.analytics.beans.Question;
import com.revature.autosurvey.analytics.beans.Response;
import com.revature.autosurvey.analytics.beans.Response.WeekNum;
import com.revature.autosurvey.analytics.beans.Survey;
import com.revature.autosurvey.analytics.beans.Question.QuestionType;
import com.revature.autosurvey.analytics.data.ResponseDao;
import com.revature.autosurvey.analytics.data.SurveyDao;
import com.revature.autosurvey.analytics.services.ReportServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)

public class ReportServiceImplTest {

	@TestConfiguration
	static class Configuration {
		@Bean
		public ReportServiceImpl getReportService(ResponseDao responseDao, SurveyDao surveyDao) {
			ReportServiceImpl reportService = new ReportServiceImpl();
			reportService.setResponseDao(responseDao);
			reportService.setSurveyDao(surveyDao);
			return reportService;
		}
	}
	
	@Autowired
	private ReportServiceImpl repService;
	
	@MockBean
	private ResponseDao responseDao;
	@MockBean
	private SurveyDao surveyDao;
	
	private static Flux<Response> responses;
	private static Mono<Survey> survey;
	
	@BeforeAll
	public static void addResponses() {
		Map<String, String> surveyResponse1 = new HashMap<>();
		surveyResponse1.put("mctest", "1");
		surveyResponse1.put("avgTest", "1");
		Response testResponse1 = new Response(
				UUID.randomUUID(),
				"test batchname", 
				WeekNum.A, 
				UUID.randomUUID(), 
				surveyResponse1);
		
		Map<String, String> surveyResponse2 = new HashMap<>();
		surveyResponse2.put("mctest", "2");
		surveyResponse2.put("avgTest", "2");
		Response testResponse2 = new Response(
				UUID.randomUUID(), 
				"test batchname", 
				WeekNum.A, 
				UUID.randomUUID(), 
				surveyResponse2);
		
		Map<String, String> surveyResponse3 = new HashMap<>();
		surveyResponse3.put("mctest", "2");
		surveyResponse3.put("avgTest", "2");
		Response testResponse3 = new Response(
				UUID.randomUUID(), 
				"test batchname", 
				WeekNum.A, 
				UUID.randomUUID(), 
				surveyResponse3);
		
		Map<String, String> surveyResponse4 = new HashMap<>();
		surveyResponse4.put("mctest", "2");
		surveyResponse4.put("avgTest", "2");
		Response testResponse4 = new Response(
				UUID.randomUUID(), 
				"test batchname", 
				WeekNum.A, 
				UUID.randomUUID(), 
				surveyResponse4);
		
		Map<String, String> surveyResponse5 = new HashMap<>();
		surveyResponse5.put("mctest", "");
		surveyResponse5.put("avgTest", "");
		Response testResponse5 = new Response(
				UUID.randomUUID(), 
				"test batchname", 
				WeekNum.A, 
				UUID.randomUUID(), 
				surveyResponse5);
		
		responses=Flux.just(testResponse1, testResponse2, testResponse3, testResponse4, testResponse5);
	}
	
	@BeforeAll
	public static void addSurvey() {
		List<Question> questionsList = new ArrayList<Question>();
		Question question = new Question();
		question.setQuestionType(QuestionType.MULTIPLE_CHOICE);
		question.setTitle("mctest");
		question.setHelpText("test help text");
		question.setIsRequired(true);
		List<String> choices = new ArrayList<String>();
		choices.add("1");
		choices.add("2");
		question.setChoices(choices);
		question.setHasOtherOption(false);
		questionsList.add(question);
		
		Question q2= new Question();
		q2.setQuestionType(QuestionType.SHORT_ANSWER);//don't have num yet
		q2.setTitle("avgTest");
		questionsList.add(q2);
		
		
		
		survey=Mono.just(new Survey(
				
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
	
	
	
	@Test
	void basicFunctionality() {
		Mockito.when(responseDao.getResponses("1","A")).thenReturn(responses);
		Mockito.when(surveyDao.getSurvey("1")).thenReturn(survey);
		Map<String,Double> mctest= new HashMap<>();
		Map<String,Map<String,Double>> percentages= new HashMap<>();
		Map<String, Double> averages=new HashMap<>();
		mctest.put("1", 0.25);
		mctest.put("2", 0.75);
		percentages.put("mctest", mctest);
		averages.put("avgTest", 1.75);
	 	assertEquals(percentages,repService.getReport("1", "A").block().getPercentages(), "We should get a 50/50 split");
	 	assertEquals(averages, repService.getReport("1", "A").block().getAverages(),"We should get the average of 1 and 2");
	}
	
	

}
