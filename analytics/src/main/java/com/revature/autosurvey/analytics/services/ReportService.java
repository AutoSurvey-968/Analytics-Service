package com.revature.autosurvey.analytics.services;

import com.revature.autosurvey.analytics.beans.Report;

import reactor.core.publisher.Flux;

public interface ReportService {

	Flux<Report> getReport(String surveyId);

}
