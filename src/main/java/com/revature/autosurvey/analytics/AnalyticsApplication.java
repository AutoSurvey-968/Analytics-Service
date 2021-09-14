package com.revature.autosurvey.analytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

import com.revature.autosurvey.analytics.beans.Report;
import com.revature.autosurvey.analytics.services.ReportServiceImpl;

@SpringBootApplication
@EnableEurekaClient
public class AnalyticsApplication {

	private static Logger log = LoggerFactory.getLogger(AnalyticsApplication.class);
	
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(AnalyticsApplication.class, args);
		Report report = ctx.getBean(ReportServiceImpl.class).getReport("12345678-1234-1234-1234-123456789abc").block();
		if (report != null) {
			log.trace(report.toString());
		} else {
			log.trace("report null");
		}
	}
}
