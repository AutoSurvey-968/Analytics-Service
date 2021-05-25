package com.revature.autosurvey.analytics.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.autosurvey.analytics.beans.Question.QuestionType;
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
	public void setResponseDao(ResponseDao responseDao) {
		this.responseDao=responseDao;
	}
	
	@Autowired
	private SurveyDao surveyDao;
	@Autowired
	public void setSurveyDao(SurveyDao surveyDao) {
		this.surveyDao=surveyDao;
	}

	@Override
	public Mono<Report> getReport(String surveyId) {
		Mono<Survey> survey = surveyDao.getSurvey(surveyId);
		Flux<Response> responses = responseDao.getResponses(surveyId);
		return createReport(survey,responses);
	}
	
	@Override
	public Mono<Report> getReport(String surveyId, String weekEnum) {
		
		Mono<Survey> survey = surveyDao.getSurvey(surveyId);
		Flux<Response> responses = responseDao.getResponses(surveyId, weekEnum);
		return createReport(survey,responses);
	}

	@Override
	public Mono<Report> createReport(Mono<Survey> survey, Flux<Response> responses) {

		/*
		 * flatMap contents of survey to be used with a map of the contents of responses.
		 * The map of the list of responses' content will return a Mono of Report that's been constructed with populated fields to the flatMap,
		 * flatMap will return the Mono of the previous map.
		 */
		return survey.flatMap(s -> responses.collectList().map(r -> {
			// Eventually will pull the right survey/response by the correct surveyId. For now just grabbing all the test survey/responses.
				Report report = new Report(s.getUuid().toString()); // Testing to see if we get the UUID from Survey, will probably change type of Report.getSurveyId to UUID later
				//for each response, check questiontype
				report.setAverages(new HashMap<>());
				report.setPercentages(new HashMap<>());
				s.getQuestions().forEach(question -> {
					
					//currently using short answer because number doesn't exist
					if(question.getQuestionType() == QuestionType.SHORT_ANSWER) {
						Double average = 0.0;
						int size= r.size();

						//if processible make an average and add to report
						for(int i = 0; i < r.size(); i++) {
							if(r.get(i).getSurveyResponses().get(question.getTitle())!=null&&r.get(i).getSurveyResponses().get(question.getTitle())!=""){
								average += Double.valueOf(r.get(i).getSurveyResponses().get(question.getTitle()));

							}else {
								size--;
							}
						}
						average /= size;
						report.getAverages().put(question.getTitle(), average);
					}
					else if(question.getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
						Map<String, Double> choicesMap = new HashMap<>();
						int total = 0;
						question.getChoices().forEach(choice -> {
							choicesMap.put(choice, 0.0);
							
						});//adding up choices
						for(int i = 0; i < r.size(); i++) {
							String questionTitle = r.get(i).getSurveyResponses().get(question.getTitle());
							if(choicesMap.keySet().contains(questionTitle)) {
								double value = choicesMap.get(questionTitle);
								choicesMap.put(questionTitle, ++value);
								total++;
							}
							
						}//creating percentages
				        for (String choice : choicesMap.keySet()) {
							double result = choicesMap.get(choice);
							choicesMap.put(choice, result/total);
				        }
						report.getPercentages().put(question.getTitle(), choicesMap);
					}
				});

				return report;
			})
		);
	}
	
}
