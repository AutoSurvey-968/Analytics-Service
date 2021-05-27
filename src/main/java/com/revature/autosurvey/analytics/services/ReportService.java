package com.revature.autosurvey.analytics.services;

import org.springframework.http.ResponseEntity;

import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.beans.Response.WeekEnum;

import reactor.core.publisher.Mono;

public interface ReportService {

	Mono<Report> getReport(String surveyId);

	Mono<Report> getReport(String surveyId, WeekEnum weekEnum);

	Mono<Report> getReport(String surveyId, WeekEnum weekEnum, String batchName);

}
