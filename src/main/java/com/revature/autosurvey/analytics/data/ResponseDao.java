package com.revature.autosurvey.analytics.data;

import com.revature.autosurvey.analytics.beans.Response;
import com.revature.autosurvey.analytics.beans.Response.WeekEnum;

import reactor.core.publisher.Flux;

public interface ResponseDao {

	Flux<Response> getResponses(String surveyId);

	Flux<Response> getResponses(String surveyId, WeekEnum weekEnum);

}