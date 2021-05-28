package com.revature.autosurvey.analytics.beans;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public List<String> getMappedQuestions() {
		return mappedQuestions;
	}
	public void setMappedQuestions(List<String> mappedQuestions) {
		this.mappedQuestions = mappedQuestions;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
