package com.revature.autosurvey.analytics.services;

import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.beans.Response;
import com.revature.autosurvey.analytics.beans.Survey;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReportService {

	Mono<Report> getReport(String surveyId);

	Mono<Report> getReport(String surveyId, String weekEnum);

}
