package com.revature.autosurvey.analytics.data;

import net.minidev.json.JSONObject;
import reactor.core.publisher.Flux;

public interface SurveyDao {

	Flux<JSONObject> getSurvey(String surveyId);

}