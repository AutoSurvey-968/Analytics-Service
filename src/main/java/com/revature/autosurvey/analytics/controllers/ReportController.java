package com.revature.autosurvey.analytics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.services.ReportService;

import reactor.core.publisher.Flux;


@RestController
@RequestMapping(value = "/reports")
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@GetMapping
	public Flux<Report> getReport(String surveyId) {
		return reportService.getReport(surveyId);
	}
}
