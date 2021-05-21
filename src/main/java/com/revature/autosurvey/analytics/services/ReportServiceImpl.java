package com.revature.autosurvey.analytics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.beans.Response;
import com.revature.autosurvey.analytics.beans.Survey;
import com.revature.autosurvey.analytics.data.ResponseDao;
import com.revature.autosurvey.analytics.data.SurveyDao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ResponseDao responseDao;
	
	@Autowired
	private SurveyDao surveyDao;

	@Override
	public Mono<Report> getReport(String surveyId) {
		Report report = new Report(surveyId);
		Mono<Survey> survey = surveyDao.getSurvey(surveyId);
		Flux<Response> responses = responseDao.getResponses(surveyId);
		survey.doOnNext((s) ->
			System.out.println(s)
		);
		//responses.
		//for each response, check questiontype
			//if processable make an average and add to report
		return Mono.just(report);
	}
	
	@Override
	public Mono<Report> getReport(String surveyId, String weekEnum) {
		
		return getReport(surveyId);
	}
	
}
