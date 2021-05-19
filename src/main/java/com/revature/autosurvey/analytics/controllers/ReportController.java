package com.revature.autosurvey.analytics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.services.ReportService;

import net.minidev.json.JSONObject;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping(value = "/reports")
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@GetMapping(params = {"surveyId"})
	public Flux<JSONObject> getReport(String surveyId) {
		return reportService.getReport(surveyId);
	}
	
	@GetMapping(params = {"surveyId", "weekEnum"})
	public Flux<JSONObject> getReport(String surveyId, String weekEnum) {
		return reportService.getReport(surveyId, weekEnum);
	}
}
