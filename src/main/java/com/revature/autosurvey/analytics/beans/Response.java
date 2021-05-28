package com.revature.autosurvey.analytics.beans;

import java.util.Map;
import java.util.UUID;

public class Response {

	private UUID responseId;
	private String batchName;

	private String week;
	private UUID surveyId;
	private Map<String, String> surveyResponses;

	public Response(UUID responseId, String batchName, String week, UUID surveyId,
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

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
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
