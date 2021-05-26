package com.revature.autosurvey.analytics.controllers;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.intuit.karate.junit5.Karate;

@ExtendWith(SpringExtension.class)
public class ReportControllerTest {
	@Karate.Test
	Karate testReportController() {
		return Karate.run("reportController").relativeTo(getClass());
	}
	
}
