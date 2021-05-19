package com.revature.autosurvey.analytics.services;

import net.minidev.json.JSONObject;
import reactor.core.publisher.Flux;

public interface ReportService {

	Flux<JSONObject> getReport(String surveyId);

	Flux<JSONObject> getReport(String surveyId, String weekEnum);

}
