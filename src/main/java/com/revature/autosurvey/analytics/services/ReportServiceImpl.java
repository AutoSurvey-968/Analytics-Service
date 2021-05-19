package com.revature.autosurvey.analytics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.autosurvey.analytics.data.ResponseDao;
import com.revature.autosurvey.analytics.data.SurveyDao;

import net.minidev.json.JSONObject;
import reactor.core.publisher.Flux;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ResponseDao responseDao;
	
	@Autowired
	private SurveyDao surveyDao;

	@Override
	public Flux<JSONObject> getReport(String surveyId) {
		Flux<JSONObject> surv = surveyDao.getSurvey(surveyId);
		Flux<JSONObject> resp = responseDao.getResponses(surveyId);
		return surv;
	}
	
	@Override
	public Flux<JSONObject> getReport(String surveyId, String weekEnum) {
		Flux<JSONObject> surv = surveyDao.getSurvey(surveyId);
		Flux<JSONObject> resp = responseDao.getResponses(surveyId);
		return surv;
	}
	
}
