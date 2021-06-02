package com.revature.autosurvey.analytics.beans;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class Response {

	private UUID uuid;
	private String batch;

	private Date date;
	private UUID surveyUuid;
	private Map<String, String> responses;

	public Response(UUID responseId, String batchName, String week, UUID surveyId,
			Map<String, String> surveyResponses) {
		super();
		this.surveyUuid = surveyId;
		this.responses = surveyResponses;
	}

	public UUID getSurveyId() {
		return surveyUuid;
	}

	public void setSurveyId(UUID surveyId) {
		this.surveyUuid = surveyId;
	}

	public Map<String, String> getSurveyResponses() {
		return responses;
	}

	public void setSurveyResponses(Map<String, String> surveyResponses) {
		this.responses = surveyResponses;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UUID getSurveyUuid() {
		return surveyUuid;
	}

	public void setSurveyUuid(UUID surveyUuid) {
		this.surveyUuid = surveyUuid;
	}

	public Map<String, String> getResponses() {
		return responses;
	}

	public void setResponses(Map<String, String> responses) {
		this.responses = responses;
	}
}
