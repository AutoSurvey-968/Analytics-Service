package com.revature.autosurvey.analytics.services;

import java.util.HashMap;
import java.util.Map;

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
		Mono<Survey> survey = surveyDao.getSurvey(surveyId);
		Flux<Response> responses = responseDao.getResponses(surveyId);
		/*
		 * flatMap contents of survey to be used with a map of the contents of responses.
		 * The map of the contents of responses will return a Report object that's been constructed with populated fields to the flatMap,
		 * flatMap will return a Mono of Report.
		 */
		return survey.flatMap(s -> responses.collectList().map(r -> {
			// Eventually will pull the right survey/response by the correct surveyId. For now just grabbing all the test survey/responses.
				Report report = new Report(s.getUuid().toString()); // Testing to see if we get the UUID from Survey, will probably change type of Report.getSurveyId to UUID later
				Double average = 0.0;
				//for each response, check questiontype
				//if processable make an average and add to report
				for(int i = 0; i < r.size(); i++) {
					average += Double.valueOf(r.get(i).getSurveyResponses().get("test"));
				}
				average /= r.size();
				Map<String, Double> averageMap = new HashMap<>(); // Create a new HashMap to populate it before adding it to report.
				averageMap.put("test", average);
				report.setAverages(averageMap);
				return report;
			})
		);
	}
	
	@Override
	public Mono<Report> getReport(String surveyId, String weekEnum) {
		
		return getReport(surveyId);
	}
	
}
