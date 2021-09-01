package com.revature.autosurvey.analytics.beans;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Survey {
	private UUID uuid;
	private LocalDateTime createdOn;
	private String title;
	private String description;
	private String confirmation;
	private String version;
	private List<String> mappedQuestions;
	private List<Question> questions;
	

	public Survey(UUID uuid, LocalDateTime createdOn, String title, String description, String confirmation,
			String version, List<String> mappedQuestions, List<Question> questions) {
		super();
		this.uuid = uuid;
		this.createdOn = createdOn;
		this.title = title;
		this.description = description;
		this.confirmation = confirmation;
		this.version = version;
		this.mappedQuestions = mappedQuestions;
		this.questions = questions;
	}
	public Survey() {
		super();
	}
}
