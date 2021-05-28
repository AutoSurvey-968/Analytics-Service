package com.revature.autosurvey.analytics.services;

import com.revature.autosurvey.analytics.beans.Report;

import reactor.core.publisher.Mono;

public interface ReportService {

	Mono<Report> getReport(String surveyId);

	Mono<Report> getReport(String surveyId, String weekDay);

	Mono<Report> getReport(String surveyId, String weekDay, String batchName);

}
