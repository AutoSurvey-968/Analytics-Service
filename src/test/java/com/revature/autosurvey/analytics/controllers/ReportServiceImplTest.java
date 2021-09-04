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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.autosurvey.analytics.beans.AnalyticsData;
import com.revature.autosurvey.analytics.beans.Question;
import com.revature.autosurvey.analytics.beans.Question.QuestionType;
import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.beans.Response;
import com.revature.autosurvey.analytics.beans.Survey;
import com.revature.autosurvey.analytics.data.ResponseDao;
import com.revature.autosurvey.analytics.data.SurveyDao;
import com.revature.autosurvey.analytics.services.ReportServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)

class ReportServiceImplTest {

	@Mock
	private ResponseDao responseDao;
	@Mock
	private SurveyDao surveyDao;
	@InjectMocks
	private ReportServiceImpl repService = new ReportServiceImpl(responseDao, surveyDao);
	
	private static Flux<Response> responses;
	private static Flux<Response> oldresponses;
	private static Mono<Survey> survey;
	private static String EmptyExample = "2021-05-10";
	private static Flux<Response> emptyResponses;
	private static String A = "2021-05-17";
	private static String B = "2021-05-24";
	
	@BeforeAll
	public static void addResponses() {
		
		// each is a response to a set question.
		Map<String, String> surveyResponse1 = new HashMap<>();
		surveyResponse1.put("mctest", "1");
		surveyResponse1.put("avgTest", "4");
		Response testResponse1 = new Response(
				UUID.randomUUID(),
				"test batchname", 
				B, 
				UUID.randomUUID(), 
				surveyResponse1);
		
		Map<String, String> surveyResponse2 = new HashMap<>();
		surveyResponse2.put("mctest", "1");
		surveyResponse2.put("avgTest", "2");
		Response testResponse2 = new Response(
				UUID.randomUUID(), 
				"test batchname", 
				B, 
				UUID.randomUUID(), 
				surveyResponse2);
		
		Map<String, String> surveyResponse3 = new HashMap<>();
		surveyResponse3.put("mctest", "1");
		surveyResponse3.put("avgTest", "2");
		Response testResponse3 = new Response(
				UUID.randomUUID(), 
				"test batchname", 
				B, 
				UUID.randomUUID(), 
				surveyResponse3);
		
		Map<String, String> surveyResponse4 = new HashMap<>();
		surveyResponse4.put("mctest", "1");
		surveyResponse4.put("avgTest", "2");
		Response testResponse4 = new Response(
				UUID.randomUUID(), 
				"test batchname", 
				B, 
				UUID.randomUUID(), 
				surveyResponse4);
		
		Map<String, String> surveyResponse5 = new HashMap<>();
		surveyResponse5.put("mctest", "");
		surveyResponse5.put("avgTest", "");
		Response testResponse5 = new Response(
				UUID.randomUUID(), 
				"test batchname", 
				B, 
				UUID.randomUUID(), 
				surveyResponse5);
		
		responses=Flux.just(testResponse1, testResponse2, testResponse3, testResponse4, testResponse5);
		oldresponses = generateOldResponses();
		addSurvey();
	}
	
	private static Flux<Response> generateOldResponses() {
		
		//generates old responses for delta.
		Map<String, String> surveyResponse1 = new HashMap<>();
		surveyResponse1.put("mctest", "1");
		surveyResponse1.put("avgTest", "1");
		Response testResponse1 = new Response(
				UUID.randomUUID(),
				"test batchname", 
				A, 
				UUID.randomUUID(), 
				surveyResponse1);
		
		Map<String, String> surveyResponse2 = new HashMap<>();
		surveyResponse2.put("mctest", "2");
		surveyResponse2.put("avgTest", "2");
		Response testResponse2 = new Response(
				UUID.randomUUID(), 
				"test batchname", 
				A, 
				UUID.randomUUID(), 
				surveyResponse2);
		
		Map<String, String> surveyResponse3 = new HashMap<>();
		surveyResponse3.put("mctest", "2");
		surveyResponse3.put("avgTest", "2");
		Response testResponse3 = new Response(
				UUID.randomUUID(), 
				"test batchname", 
				A, 
				UUID.randomUUID(), 
				surveyResponse3);
		
		Map<String, String> surveyResponse4 = new HashMap<>();
		surveyResponse4.put("mctest", "2");
		surveyResponse4.put("avgTest", "2");
		Response testResponse4 = new Response(
				UUID.randomUUID(), 
				"test batchname", 
				A, 
				UUID.randomUUID(), 
				surveyResponse4);
		
		Map<String, String> surveyResponse5 = new HashMap<>();
		surveyResponse5.put("mctest", "");
		surveyResponse5.put("avgTest", "");
		Response testResponse5 = new Response(
				UUID.randomUUID(), 
				"test batchname", 
				A, 
				UUID.randomUUID(), 
				surveyResponse5);
		
		return Flux.just(testResponse1, testResponse2, testResponse3, testResponse4, testResponse5);
	}

	public static void addSurvey() {
		//generates the survey with the two questions.
		
		List<Question> questionsList = new ArrayList<Question>();
		Question question = new Question();
		question.setQuestionType(QuestionType.RADIO);
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
		q2.setQuestionType(QuestionType.DROPDOWN);//don't have num yet
		//reuse choices from above
		q2.setChoices(choices);
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
		Mockito.when(responseDao.getResponses("1")).thenReturn(responses);
		
		Mockito.when(responseDao.getResponses("1",EmptyExample)).thenReturn(emptyResponses);
		Mockito.when(surveyDao.getSurvey("1")).thenReturn(survey);
		Map<String,AnalyticsData> mctest= new HashMap<>();
		Map<String,Map<String,AnalyticsData>> percentages= new HashMap<>();
		Map<String, AnalyticsData> averages=new HashMap<>();
		AnalyticsData oneData = new AnalyticsData();
		oneData.setDatum(1);
		AnalyticsData twoData = new AnalyticsData();
		twoData.setDatum(2.5);
		mctest.put("1", oneData);
		mctest.put("2", twoData);
		percentages.put("mctest", mctest);
		AnalyticsData avgData = new AnalyticsData();
		avgData.setDatum(2.5);
		averages.put("avgTest", avgData);
		AnalyticsData mcAvgData = new AnalyticsData();
		mcAvgData.setDatum(1.0);
		averages.put("mctest", mcAvgData);
		
		Mono<Report> monoR = repService.getReport("1");
		Mockito.verify(responseDao).getResponses("1");
		Mockito.verify(surveyDao).getSurvey("1");
		StepVerifier.create(monoR).expectNextMatches(r-> r.getAverages().equals(averages)).verifyComplete();
	 	
	}
	
	@Test
	void deltaFunctionality() {

		Mockito.when(responseDao.getResponses("1",A)).thenReturn(oldresponses);
		Mockito.when(responseDao.getResponses("1",B)).thenReturn(responses);
		Mockito.when(surveyDao.getSurvey("1")).thenReturn(survey);
		Map<String,AnalyticsData> mctest= new HashMap<>();
		Map<String,Map<String,AnalyticsData>> percentages= new HashMap<>();
		Map<String, AnalyticsData> averages=new HashMap<>();
		
		AnalyticsData oneData = new AnalyticsData();
		AnalyticsData twoData = new AnalyticsData();
		AnalyticsData avgData = new AnalyticsData();
		AnalyticsData avgMcData = new AnalyticsData();
		
	 	oneData.setDatum(1);
	 	oneData.setDelta(0.75);
	 	twoData.setDatum(0);
	 	twoData.setDelta(-0.75);
	 	avgData.setDatum(2.5);
	 	avgData.setDelta(0.75);
	 	avgMcData.setDatum(1.0);
	 	avgMcData.setDelta(-0.75);
	 	

		mctest.put("1", oneData);
		mctest.put("2", twoData);
		percentages.put("mctest", mctest);
		
		averages.put("mctest", avgMcData);
		averages.put("avgTest", avgData);
	
	 	
	 	Map<String, AnalyticsData> results = repService.getReport("1", B).block().getPercentages().get("mctest");
	 	assertEquals(percentages.get("mctest").get("1").getDatum(),results.get("1").getDatum(), "We should get a '1' as the data");
	 	assertEquals(percentages.get("mctest").get("1").getDelta(),results.get("1").getDelta(), "We should get a delta of 0.75");
	 	assertEquals(percentages.get("mctest").get("2"),results.get("2"), "We should get a '0' as the data with a delta of -0.75");
	 	assertEquals(averages, repService.getReport("1", B).block().getAverages(),"We should get the average of 2, 2, 2, and 4, with a delta of 0.75");
	}
	
	

}
