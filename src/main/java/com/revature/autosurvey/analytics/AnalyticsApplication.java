package com.revature.autosurvey.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

import com.revature.autosurvey.analytics.services.ReportServiceImpl;

@SpringBootApplication
@EnableEurekaClient
public class AnalyticsApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(AnalyticsApplication.class, args);
		
		System.out.println(ctx.getBean(ReportServiceImpl.class).getReport("12345678-1234-1234-1234-123456789abc").block().toString());
		
	}

}
