package com.revature.autosurvey.analytics.controllers;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.services.ReportService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class ReportControllerTest {
	@TestConfiguration
	static class Configuration {
		@Bean
		public ReportController getIoController(ReportService reportService) {
			ReportController reportController = new ReportController();
			reportController.setReportService(reportService);
			return reportController;
		}

		@Bean
		public ReportService getReportService() {
			return Mockito.mock(ReportService.class);
		}

	}
	
	@Autowired
	private ReportController reportController;
	
	@MockBean
	private ReportService reportService;
	
	@Test
	void testGetReportIdValid() {
		String surveyId = "new ID";
		
		when(reportService.getReport(surveyId)).thenReturn(Mono.just(new Report(surveyId)));
		
		Mono<ResponseEntity<Report>> monoReport = reportController.getReport(surveyId);
		
		StepVerifier.create(monoReport).expectNext(ResponseEntity.status(200).body(new Report(surveyId)));
		
	}
	
	@Test
	void testGetReportIdNotFound() {
		String surveyId = "new ID";
		
		when(reportService.getReport(surveyId)).thenReturn(Mono.just(new Report(null)));
		
		Mono<ResponseEntity<Report>> monoReport = reportController.getReport(surveyId);
		
		StepVerifier.create(monoReport).expectNext(ResponseEntity.status(404).build());
		
	}
	
	@Test
	void testGetReportIdWeekDayValid() {
		String surveyId = "new ID";
		String weekDay = "2021-01-01";
		
		when(reportService.getReport(surveyId, weekDay)).thenReturn(Mono.just(new Report(surveyId)));
		
		Mono<ResponseEntity<Report>> monoReport = reportController.getReport(surveyId, weekDay);
		
		StepVerifier.create(monoReport).expectNext(ResponseEntity.status(200).body(new Report(surveyId)));
		
	}
	
	@Test
	void testGetReportIdWeekDayNotFound() {
		String surveyId = "new ID";
		String weekDay = "2021-01-01";
		
		when(reportService.getReport(surveyId, weekDay)).thenReturn(Mono.just(new Report(null)));
		
		Mono<ResponseEntity<Report>> monoReport = reportController.getReport(surveyId, weekDay);
		
		StepVerifier.create(monoReport).expectNext(ResponseEntity.status(404).build());
		
	}
	
	@Test
	void testGetReportIdWeekDayBatchNameValid() {
		String surveyId = "new ID";
		String weekDay = "2021-01-01";
		String batchName = "mock Batch";
		
		when(reportService.getReport(surveyId, weekDay, batchName)).thenReturn(Mono.just(new Report(surveyId)));
		
		Mono<ResponseEntity<Report>> monoReport = reportController.getReport(surveyId, weekDay, batchName);
		
		StepVerifier.create(monoReport).expectNext(ResponseEntity.status(200).body(new Report(surveyId)));
		
	}
	
	@Test
	void testGetReportIdWeekDayBatchNameNotFound() {
		String surveyId = "new ID";
		String weekDay = "2021-01-01";
		String batchName = "mock Batch";
		
		when(reportService.getReport(surveyId, weekDay, batchName)).thenReturn(Mono.just(new Report(null)));
		
		Mono<ResponseEntity<Report>> monoReport = reportController.getReport(surveyId, weekDay, batchName);
		
		StepVerifier.create(monoReport).expectNext(ResponseEntity.status(404).build());
		
	}
}
