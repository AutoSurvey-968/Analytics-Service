package com.revature.autosurvey.analytics.beans;

public class Report {
	private String surveyId;
	private String weekEnum;
	
	public Report(String surveyId, String weekEnum) {
		this.surveyId=surveyId;
		this.weekEnum=weekEnum;
	}
	public Report(String surveyId) {
		this.surveyId=surveyId;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public String getWeekEnum() {
		return weekEnum;
	}

	public void setWeekEnum(String weekEnum) {
		this.weekEnum = weekEnum;
	}
}
