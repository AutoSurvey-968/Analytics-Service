package com.revature.autosurvey.analytics.services;

import org.springframework.stereotype.Service;

import com.revature.autosurvey.analytics.beans.Report;

import reactor.core.publisher.Flux;

@Service
public class ReportServiceImpl implements ReportService {

	@Override
	public Flux<Report> getReport(String surveyId) {
		return null;
	}
	
}
