package com.revature.autosurvey.analytics.data;

import com.revature.autosurvey.analytics.beans.Response;

import reactor.core.publisher.Flux;

public interface ResponseDao {

	Flux<Response> getResponses(String surveyId);

	Flux<Response> getResponses(String surveyId, String weekDay);

	Flux<Response> getResponses(String surveyId, String weekDay, String batchName);

}