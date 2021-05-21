package com.revature.autosurvey.analytics.data;

import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.beans.Survey;

import reactor.core.publisher.Mono;

public interface SurveyDao {

	Mono<Survey> getSurvey(String surveyId);

}