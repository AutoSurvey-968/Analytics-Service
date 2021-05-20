package com.revature.autosurvey.analytics.data;

import com.revature.autosurvey.analytics.beans.Report;

import reactor.core.publisher.Mono;

public interface ResponseDao {

	Mono<Report> getResponses(String surveyId);

}