package com.revature.autosurvey.analytics.beans;

import java.util.Map;

import lombok.Data;

@Data
public class Report {
	private String surveyId;
	private String weekDay;
	private String batchString;
	private Map<String, AnalyticsData> averages; //numeric
	private Map<String, Map<String, AnalyticsData>> percentages; //multiple choice
	
	public Report(String surveyId, String weekDay) {
		this.surveyId=surveyId;
		this.weekDay=weekDay;
	}
	public Report(String surveyId) {
		this.surveyId=surveyId;
	}
	public Report() {
		super();
	}
}
