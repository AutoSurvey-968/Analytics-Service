package com.revature.autosurvey.analytics.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.autosurvey.analytics.beans.Data;
import com.revature.autosurvey.analytics.beans.Question;
import com.revature.autosurvey.analytics.beans.Question.QuestionType;
import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.beans.Response;
import com.revature.autosurvey.analytics.beans.Response.WeekEnum;
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
	public Mono<Report> getReport(String surveyId, WeekEnum weekEnum, String batchName) {
		
		Mono<Survey> survey = surveyDao.getSurvey(surveyId);
		Flux<Response> responses = responseDao.getResponses(surveyId, weekEnum, batchName);
		if(weekEnum!=WeekEnum.A) {

			Flux<Response> oldResponses = responseDao.getResponses(surveyId, WeekEnum.values()[weekEnum.ordinal()-1], batchName);
			Mono<Report> oldReport = createReport(survey, oldResponses);
			Mono<Report> newReport = createReport(survey, responses);
			return addDeltaToReport(oldReport, newReport);
		} else {
			return createReport(survey,responses);
		}
	}
	
	@Override
	public Mono<Report> getReport(String surveyId, WeekEnum weekEnum) {
		
		Mono<Survey> survey = surveyDao.getSurvey(surveyId);
		Flux<Response> responses = responseDao.getResponses(surveyId, weekEnum);
		if(weekEnum!=WeekEnum.A) {

			Flux<Response> oldResponses = responseDao.getResponses(surveyId, WeekEnum.values()[weekEnum.ordinal()-1]);
			Mono<Report> oldReport = createReport(survey, oldResponses);
			Mono<Report> newReport = createReport(survey, responses);
			return addDeltaToReport(oldReport, newReport);
		} else {
			return createReport(survey,responses);
		}
	}

	private Mono<Report> addDeltaToReport(Mono<Report> oldReport, Mono<Report> newReport) {
		return newReport.flatMap(report -> 
			oldReport.map(old -> {
				Map<String, Data> averages = report.getAverages();
				for(Entry<String, Data> question : averages.entrySet()) {
					Data newData = question.getValue();
					Data oldData = old.getAverages().get(question.getKey());
					newData.setDelta(newData.getDatum()-oldData.getDatum());
				}
				Map<String, Map<String, Data>> percentages = report.getPercentages();
				for(Entry<String, Map<String, Data>> question : percentages.entrySet()) {
					Map<String, Data> sub = question.getValue();
					for(Entry<String, Data> option : sub.entrySet()) {
						Data newData = option.getValue();
						Data oldData = old.getPercentages().get(question.getKey()).get(option.getKey());
						newData.setDelta(newData.getDatum()-oldData.getDatum());
					}
				}
				return report;
			})
		);
	}

	private Mono<Report> createReport(Mono<Survey> survey, Flux<Response> responses) {

		/*
		 * flatMap contents of survey to be used with a map of the contents of responses.
		 * The map of the list of responses' content will return a Mono of Report that's been constructed with populated fields to the flatMap,
		 * flatMap will return the Mono of the previous map.
		 */
		return survey.flatMap(s -> responses.collectList().map(r -> {
				Report report = new Report(s.getUuid().toString()); // Testing to see if we get the UUID from Survey, will probably change type of Report.getSurveyId to UUID later
				report.setAverages(new HashMap<>());
				report.setPercentages(new HashMap<>());
				s.getQuestions().forEach(question -> {
					
					//currently using short answer because number doesn't exist
					if(question.getQuestionType() == QuestionType.SHORT_ANSWER) {
						Data d = new Data();
						d.setDatum(average(question,r));
						report.getAverages().put(question.getTitle(), d);
					}
					else if(question.getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
						report.getPercentages().put(question.getTitle(), percentages(question, r));
					}
				});

				return report;
			})
		);
	}
	
	private Double average(Question question, List<Response> r) {
		Double average = 0.0;
		int size= r.size();

		//if processible make an average and add to report
		for(int i = 0; i < r.size(); i++) {
			if(r.get(i).getSurveyResponses().get(question.getTitle())!=null&&!r.get(i).getSurveyResponses().get(question.getTitle()).equals("")){
				average += Double.valueOf(r.get(i).getSurveyResponses().get(question.getTitle()));

			}else {
				size--;
			}
		}
		average /= size;
		return average;
	}

	private Map<String, Data> percentages(Question question, List<Response> r){
		Map<String, Data> choicesMap = new HashMap<>();
		int total = 0;
		question.getChoices().forEach(choice -> {

			Data d = new Data();
			d.setDatum(0.0);
			choicesMap.put(choice, d);
		}

		);
		//adding up choices
		for(int i = 0; i < r.size(); i++) {
			String questionTitle = r.get(i).getSurveyResponses().get(question.getTitle());
			if(choicesMap.keySet().contains(questionTitle)) {
				double value = choicesMap.get(questionTitle).getDatum();
				Data d = new Data();
				d.setDatum(++value);
				choicesMap.put(questionTitle, d);
				total++;
			}
			
		}//creating percentages
        for (Map.Entry<String, Data> choiceEntry : choicesMap.entrySet()) {
			Data result = choiceEntry.getValue();
			if(total!=0) {
				result.setDatum(result.getDatum()/total);
				choicesMap.put(choiceEntry.getKey(), result);
			}
        }
        return choicesMap;
	}
	
}
