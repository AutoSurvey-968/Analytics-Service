package com.revature.autosurvey.analytics.beans;

import java.util.Map;

public class Report {
	private String surveyId;
	private String weekDay;
	private String batchString;
	private Map<String, Data> averages; //numeric
	private Map<String, Map<String, Data>> percentages; //multiple choice
	
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

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekEnum) {
		this.weekDay = weekEnum;
	}
	public String getBatchString() {
		return batchString;
	}
	public void setBatchString(String batchString) {
		this.batchString = batchString;
	}
	public Map<String, Data> getAverages() {
		return averages;
	}
	public void setAverages(Map<String, Data> averages) {
		this.averages = averages;
	}
	public Map<String, Map<String, Data>> getPercentages() {
		return percentages;
	}
	public void setPercentages(Map<String, Map<String, Data>> percentages) {
		this.percentages = percentages;
	}
}
