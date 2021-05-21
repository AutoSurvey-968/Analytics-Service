package com.revature.autosurvey.analytics.beans;

import java.util.List;

public class Question {
	private QuestionType questionType;
	private String title;
	private String helpText;
	private Boolean isRequired;
	private List<String> choices;
	private Boolean hasOtherOption;

	public enum QuestionType {
		CHECKBOX, DROPDOWN, MULTIPLE_CHOICE, PARAGRAPH, RADIO, SHORT_ANSWER
	}
	
	public QuestionType getQuestionType() {
		return questionType;
	}
	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHelpText() {
		return helpText;
	}
	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}
	public Boolean getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}
	public List<String> getChoices() {
		return choices;
	}
	public void setChoices(List<String> choices) {
		this.choices = choices;
	}
	public Boolean getHasOtherOption() {
		return hasOtherOption;
	}
	public void setHasOtherOption(Boolean hasOtherOption) {
		this.hasOtherOption = hasOtherOption;
	}
}
