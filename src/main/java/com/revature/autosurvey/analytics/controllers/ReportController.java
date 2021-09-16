package com.revature.autosurvey.analytics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.services.ReportService;

import reactor.core.publisher.Mono;


@RestController
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	
	@GetMapping(params = {"surveyId"})
	public Mono<ResponseEntity<Report>> getReport(String surveyId) {
		return reportService.getReport(surveyId).map(report -> ResponseEntity.status(200).body(report));
	}
	
	@GetMapping(params = {"surveyId", "weekDay"})
	public Mono<ResponseEntity<Report>> getReport(String surveyId, String weekDay) {
		return reportService.getReport(surveyId, weekDay).map(report -> ResponseEntity.status(200).body(report));
	}
	
	@GetMapping(params = {"surveyId", "weekDay", "batchName"})
	public Mono<ResponseEntity<Report>> getReport(String surveyId, String weekDay, String batchName) {
		return reportService.getReport(surveyId, weekDay, batchName).map(report -> ResponseEntity.status(200).body(report));
	}
}
