package com.revature.autosurvey.analytics.beans;

public class Report {
	private String surveyId;

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public Report(String surveyId) {
		this.surveyId=surveyId;
	}
}
