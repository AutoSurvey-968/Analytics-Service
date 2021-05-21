package com.revature.autosurvey.analytics.beans;

import java.util.Map;

public class Report {
	private String surveyId;
	private String weekEnum;
	private String batchString;
	private Map<String, Double> averages; //numeric
	private Map<String, Map<String, Double>> percentages; //multiple choice
	
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
	
	public Map<String, Double> getAverages() {
		return averages;
	}
	public void setAverages(Map<String, Double> averages) {
		this.averages = averages;
	}
	public Map<String, Map<String, Double>> getPercentages() {
		return percentages;
	}
	public void setPercentages(Map<String, Map<String, Double>> percentages) {
		this.percentages = percentages;
	}
	public String getBatchString() {
		return batchString;
	}
	public void setBatchString(String batchString) {
		this.batchString = batchString;
	}
}
