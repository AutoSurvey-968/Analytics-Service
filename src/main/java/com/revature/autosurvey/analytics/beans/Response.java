package com.revature.autosurvey.analytics.beans;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import lombok.Data;

@Data
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
}
