package com.revature.autosurvey.analytics.data;

import net.minidev.json.JSONObject;
import reactor.core.publisher.Flux;

public interface ResponseDao {

	Flux<JSONObject> getResponses(String surveyId);

}