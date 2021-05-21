package com.revature.autosurvey.analytics.data;

import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.beans.Response;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ResponseDao {

	Flux<Response> getResponses(String surveyId);

}