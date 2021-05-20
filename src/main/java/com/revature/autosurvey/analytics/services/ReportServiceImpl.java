package com.revature.autosurvey.analytics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.data.ResponseDao;
import com.revature.autosurvey.analytics.data.SurveyDao;

import reactor.core.publisher.Mono;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ResponseDao responseDao;
	
	@Autowired
	private SurveyDao surveyDao;

	@Override
	public Mono<Report> getReport(String surveyId) {
		
		return surveyDao.getSurvey(surveyId);
	}
	
	@Override
	public Mono<Report> getReport(String surveyId, String weekEnum) {
		
		return responseDao.getResponses(surveyId);

	}
	
}
