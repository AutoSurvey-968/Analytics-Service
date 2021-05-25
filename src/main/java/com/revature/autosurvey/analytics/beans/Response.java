package com.revature.autosurvey.analytics.beans;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

public class Response {

	private UUID responseId;
	private String batchName;

	private WeekNum week;
	private UUID surveyId;
	private Map<String, String> surveyResponses;
	
	public enum WeekNum implements Serializable{
		A, B, ONE, TWO, THREE, FOUR, FIVE, SIX,
		SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE;
	}
	

	public Response(UUID responseId, String batchName, WeekNum week, UUID surveyId,
			Map<String, String> surveyResponses) {
		super();
		this.responseId = responseId;
		this.batchName = batchName;
		this.week = week;
		this.surveyId = surveyId;
		this.surveyResponses = surveyResponses;
	}

	public UUID getResponseId() {
		return responseId;
	}

	public void setResponseId(UUID responseId) {
		this.responseId = responseId;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public WeekNum getWeek() {
		return week;
	}

	public void setWeek(WeekNum week) {
		this.week = week;
	}

	public UUID getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(UUID surveyId) {
		this.surveyId = surveyId;
	}

	public Map<String, String> getSurveyResponses() {
		return surveyResponses;
	}

	public void setSurveyResponses(Map<String, String> surveyResponses) {
		this.surveyResponses = surveyResponses;
	}
}
